package com.sunmarvel.domain

import com.sunmarvel.data.repository.AppRepository
import javax.inject.Inject

class MarvelKeyUseCase@Inject constructor(
    private val repository: AppRepository

) {
    suspend fun invokeGetMarvelPrivateKey(): String {
        return repository.getMarvelPrivateApiKey()
    }

    suspend fun invokeSaveMarvelPrivateKey(privateKey: String) {
        repository.saveMarvelPrivateApiKey(privateKey)
    }

    suspend fun invokeGetMarvelPublicKey(): String {
        return repository.getMarvelPublicApiKey()
    }

    suspend fun invokeSaveMarvelPublicKey(publicKey: String) {
        repository.saveMarvelPublicApiKey(publicKey)
    }
}