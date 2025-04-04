package com.sunmarvel.features.home.domain

import com.sunmarvel.features.home.data.database.MyFavoriteMarvelCharactersData
import com.sunmarvel.features.home.data.repository.MyFavoriteRepository
import javax.inject.Inject

class MyFavoriteUseCase @Inject constructor(
    private val myFavoriteRepository: MyFavoriteRepository
) {
    suspend fun invokeGetMyFavoriteMarvelCharacters(): List<MyFavoriteMarvelCharactersData> {
        return myFavoriteRepository.getMyFavoriteMarvelCharacters()
    }

    suspend fun invokeGetMyFavoriteMarvelCharactersById(id: String): MyFavoriteMarvelCharactersData? {
        return myFavoriteRepository.getMyFavoriteMarvelCharactersById(id)
    }

    suspend fun invokeUpsert(myFavoriteMarvelCharactersData: MyFavoriteMarvelCharactersData) {
        myFavoriteRepository.upsert(myFavoriteMarvelCharactersData)
    }

    suspend fun invokeDeleteMyFavoriteMarvelCharactersById(id: String) {
        myFavoriteRepository.deleteMyFavoriteMarvelCharactersById(id)
    }
}