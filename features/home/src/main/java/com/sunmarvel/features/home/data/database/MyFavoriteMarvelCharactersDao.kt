package com.sunmarvel.features.home.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MyFavoriteMarvelCharactersDao {
    @Upsert
    suspend fun upsert(myFavoriteMarvelCharactersData: MyFavoriteMarvelCharactersData)

    @Query("SELECT * FROM marvel_characters")
    suspend fun getMyFavoriteMarvelCharacters(): List<MyFavoriteMarvelCharactersData>

    @Query("SELECT * FROM marvel_characters WHERE id = :id")
    suspend fun getMyFavoriteMarvelCharactersById(id: String): MyFavoriteMarvelCharactersData?

    @Query("DELETE FROM marvel_characters WHERE id = :id")
    suspend fun deleteMyFavoriteMarvelCharactersById(id: String)

}