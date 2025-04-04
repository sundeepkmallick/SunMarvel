package com.sunmarvel.features.home.data.repository

import com.sunmarvel.features.home.data.database.MyFavoriteDatabase
import com.sunmarvel.features.home.data.database.MyFavoriteMarvelCharactersDao
import com.sunmarvel.features.home.data.database.MyFavoriteMarvelCharactersData
import javax.inject.Inject

class MyFavoriteRepositoryImpl @Inject constructor(
    private val myFavoriteMarvelCharactersDao: MyFavoriteMarvelCharactersDao
) : MyFavoriteRepository {
    override suspend fun getMyFavoriteMarvelCharacters(): List<MyFavoriteMarvelCharactersData> {
        return myFavoriteMarvelCharactersDao.getMyFavoriteMarvelCharacters()
    }

    override suspend fun getMyFavoriteMarvelCharactersById(id: String): MyFavoriteMarvelCharactersData? {
        return myFavoriteMarvelCharactersDao.getMyFavoriteMarvelCharactersById(id)
    }

    override suspend fun upsert(myFavoriteMarvelCharactersData: MyFavoriteMarvelCharactersData) {
        myFavoriteMarvelCharactersDao.upsert(myFavoriteMarvelCharactersData)
    }

    override suspend fun deleteMyFavoriteMarvelCharactersById(id: String) {
        myFavoriteMarvelCharactersDao.deleteMyFavoriteMarvelCharactersById(id)
    }
}