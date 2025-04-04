package com.sunmarvel.features.home.data.repository

import com.sunmarvel.features.home.data.model.response.MarvelCharacters
import com.sunmarvel.features.home.data.model.response.MarvelComics
import com.sunmarvel.features.home.utils.ApiUrl
import com.sunmarvel.network.ktor.NetworkResult
import com.sunmarvel.network.ktor.RequestHandler
import com.sunmarvel.network.ktor.Scope
import javax.inject.Inject
import javax.inject.Named

class MarvelRepositoryImpl @Inject constructor(
    @Named(Scope.NONE) private val requestHandler: RequestHandler,
) : MarvelRepository {
    override suspend fun getMarvelCharacters(
        query: String,
        offset: String
    ): NetworkResult<MarvelCharacters> {
        val queryParams = mutableMapOf<String, String>()

        if (query.isNotEmpty()) {
            queryParams["name"] = query
        }
        if (offset.isNotEmpty()) {
            queryParams["offset"] = offset
        }

        return requestHandler.get<MarvelCharacters>(
            null,
            ApiUrl.getMarvelCharacters(),
            queryParams
        )
    }

    override suspend fun getMarvelComics(
        query: String,
        offset: String
    ): NetworkResult<MarvelComics> {
        val queryParams = mutableMapOf<String, String>()

        if (query.isNotEmpty()) {
            queryParams["title"] = query
        }
        if (offset.isNotEmpty()) {
            queryParams["offset"] = offset
        }

        return requestHandler.get<MarvelComics>(
            null,
            ApiUrl.getMarvelComics(),
            queryParams
        )
    }


}