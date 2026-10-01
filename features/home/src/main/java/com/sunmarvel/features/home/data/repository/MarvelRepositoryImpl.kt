package com.sunmarvel.features.home.data.repository

import com.sunmarvel.features.home.data.model.response.*
import com.sunmarvel.features.home.utils.ApiUrl
import com.sunmarvel.network.ktor.NetworkResult
import com.sunmarvel.network.ktor.RequestHandler
import com.sunmarvel.network.ktor.Scope
import javax.inject.Inject
import javax.inject.Named

class MarvelRepositoryImpl @Inject constructor(
    @Named(Scope.NONE) private val requestHandler: RequestHandler,
    @Named("openLibrary") private val openLibraryRequestHandler: RequestHandler,
) : MarvelRepository {

    override suspend fun getMarvelCharacters(
        query: String,
        offset: String
    ): NetworkResult<MarvelCharacters> {
        return when (val response = requestHandler.get<List<SuperheroCharacter>>(
            null, ApiUrl.SUPERHERO_ALL, emptyMap()
        )) {
            is NetworkResult.Success -> {
                val start = offset.toIntOrNull() ?: 0
                val filtered = response.result.filter {
                    query.isBlank() || it.name.contains(query, ignoreCase = true)
                }
                val page = filtered.drop(start).take(20)
                NetworkResult.Success(
                    MarvelCharacters(
                        data = Data(
                            offset = start.toLong(),
                            limit = 20,
                            total = filtered.size.toLong(),
                            count = page.size.toLong(),
                            results = page.map { it.toMarvelResult() }
                        )
                    )
                )
            }
            is NetworkResult.Error -> NetworkResult.Error(response.body, response.exception)
        }
    }

    override suspend fun getMarvelComics(
        query: String,
        offset: String
    ): NetworkResult<MarvelComics> {
        val start = offset.toIntOrNull() ?: 0
        val params = mutableMapOf<String, Any>(
            "q" to if (query.isBlank()) "marvel comics" else query,
            "offset" to start,
            "limit" to 20
        )
        return when (val response = openLibraryRequestHandler.get<OpenLibrarySearch>(
            null, ApiUrl.OPEN_LIBRARY_SEARCH, params
        )) {
            is NetworkResult.Success -> {
                NetworkResult.Success(
                    MarvelComics(
                        data = ComicsData(
                            offset = response.result.start.toLong(),
                            limit = 20,
                            total = response.result.numFound.toLong(),
                            count = response.result.docs.size.toLong(),
                            results = response.result.docs.mapIndexed { index, book ->
                                book.toComicResult(start + index)
                            }
                        )
                    )
                )
            }
            is NetworkResult.Error -> NetworkResult.Error(response.body, response.exception)
        }
    }

    private fun SuperheroCharacter.toMarvelResult() = Result(
        id = id,
        name = name,
        description = listOf(biography.fullName, biography.publisher, biography.placeOfBirth)
            .filter { it.isNotBlank() }
            .joinToString(" • "),
        thumbnail = Thumbnail(
            path = images.lg.ifBlank { images.md },
            extension = ""
        ),
        resourceURI = "https://akabab.github.io/superhero-api/api/id/$id.json",
        urls = emptyList()
    )

    private fun OpenLibraryBook.toComicResult(idValue: Int) = ComicsResult(
        id = idValue.toLong(),
        title = title,
        description = authorNames.joinToString(", ").ifBlank { null },
        issueNumber = firstPublishYear?.toLong() ?: 0,
        thumbnail = ComicsThumbnail(
            path = coverId?.let { "https://covers.openlibrary.org/b/id/$it-L.jpg" } ?: "",
            extension = ""
        )
    )
}
