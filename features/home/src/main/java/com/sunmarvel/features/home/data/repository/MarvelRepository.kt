package com.sunmarvel.features.home.data.repository

import com.sunmarvel.features.home.data.model.response.MarvelCharacters
import com.sunmarvel.features.home.data.model.response.MarvelComics
import com.sunmarvel.network.ktor.NetworkResult

interface MarvelRepository {
    suspend fun getMarvelCharacters(query: String, offset: String): NetworkResult<MarvelCharacters>
    suspend fun getMarvelComics(query: String, offset: String): NetworkResult<MarvelComics>
}