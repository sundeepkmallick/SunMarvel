package com.sunmarvel.features.home.data.repository

import com.sunmarvel.features.home.data.database.MyFavoriteMarvelCharactersData

interface MyFavoriteRepository {
    suspend fun getMyFavoriteMarvelCharacters(): List<MyFavoriteMarvelCharactersData>
    suspend fun getMyFavoriteMarvelCharactersById(id: String): MyFavoriteMarvelCharactersData?
    suspend fun upsert(myFavoriteMarvelCharactersData: MyFavoriteMarvelCharactersData)
    suspend fun deleteMyFavoriteMarvelCharactersById(id: String)
}