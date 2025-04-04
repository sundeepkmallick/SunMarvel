package com.sunmarvel.features.home.data.repository

import com.sunmarvel.features.home.data.model.response.Comics
import com.sunmarvel.features.home.data.model.response.Data
import com.sunmarvel.features.home.data.model.response.Events
import com.sunmarvel.features.home.data.model.response.Item
import com.sunmarvel.features.home.data.model.response.MarvelCharacters
import com.sunmarvel.features.home.data.model.response.Result
import com.sunmarvel.features.home.data.model.response.Series
import com.sunmarvel.features.home.data.model.response.Stories
import com.sunmarvel.features.home.data.model.response.StoryItem
import com.sunmarvel.features.home.data.model.response.Thumbnail
import com.sunmarvel.features.home.data.model.response.Url
import com.sunmarvel.features.home.presentation.marvelcharacters.list.MarvelCharacters
import com.sunmarvel.network.ktor.NetworkResult
import com.sunmarvel.network.ktor.RequestHandler
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class MarvelRepositoryImplTest {

    private val requestHandler: RequestHandler = mockk()
    private lateinit var repository: MarvelRepositoryImpl
    private lateinit var response: NetworkResult<MarvelCharacters>

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

/*
    @Before
    fun setUp(): Unit = runBlocking {
        Mockito.`when`(
            repository.getMarvelCharacters("3-d man", "1")
        ).thenReturn(NetworkResult.Success(testMarvelCharacters))

        repository = MarvelRepositoryImpl(requestHandler)
        response = repository.getMarvelCharacters("3-d man", "1")
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getMarvelCharacters() {
        val marvelCharacters = testMarvelCharacters

        //assertEquals(200, marvelCharacters.code)
        //assertEquals("3-D Man", marvelCharacters.data.results[0].name)

        assertEquals(4, 2+2)
    }
*/

    val testMarvelCharacters = com.sunmarvel.features.home.data.model.response.MarvelCharacters(
        code = 200,
        status = "Ok",
        copyright = "© 2025 MARVEL",
        attributionText = "Data provided by Marvel. © 2025 MARVEL",
        attributionHTML = "<a href=\"http://marvel.com\">Data provided by Marvel. © 2025 MARVEL</a>",
        etag = "a43d912da6670f9ca5fae09cd121f618ff86bd28",
        data = Data(
            offset = 0,
            limit = 20,
            total = 1,
            count = 1,
            results = listOf(
                Result(
                    id = 1011334,
                    name = "3-D Man",
                    description = "",
                    modified = "2014-04-29T14:18:17+0000",
                    thumbnail = Thumbnail(
                        path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                        extension = "jpg"
                    ),
                    resourceURI = "https://gateway.marvel.com/v1/public/characters/1011334",
                    comics = Comics(
                        available = 1,
                        collectionURI = "https://gateway.marvel.com/v1/public/characters/1011334/comics",
                        items = listOf(
                            Item(
                                resourceURI = "https://gateway.marvel.com/v1/public/comics/24571",
                                name = "Avengers: The Initiative (2007) #14"
                            )
                        ),
                        returned = 1
                    ),
                    series = Series(
                        available = 1,
                        collectionURI = "https://gateway.marvel.com/v1/public/characters/1011334/series",
                        items = listOf(
                            Item(
                                resourceURI = "https://gateway.marvel.com/v1/public/series/1945",
                                name = "Avengers: The Initiative (2007 - 2010)"
                            )
                        ),
                        returned = 1
                    ),
                    stories = Stories(
                        available = 1,
                        collectionURI = "https://gateway.marvel.com/v1/public/characters/1011334/stories",
                        items = listOf(
                            StoryItem(
                                resourceURI = "https://gateway.marvel.com/v1/public/stories/19947",
                                name = "Cover #19947",
                                type = "cover"
                            )
                        ),
                        returned = 1
                    ),
                    events = Events(
                        available = 1,
                        collectionURI = "https://gateway.marvel.com/v1/public/characters/1011334/events",
                        items = listOf(
                            Item(
                                resourceURI = "https://gateway.marvel.com/v1/public/events/269",
                                name = "Secret Invasion"
                            )
                        ),
                        returned = 1
                    ),
                    urls = listOf(
                        Url(
                            type = "detail",
                            url = "http://marvel.com/characters/74/3-d_man?utm_campaign=apiRef&utm_source=e4888fffc7fc1483e579bcc9362325ba"
                        )
                    )
                )
            )
        )
    )


}