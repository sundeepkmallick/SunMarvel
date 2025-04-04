package com.sunmarvel.data.repository

interface AppRepository {
    suspend fun getMarvelPrivateApiKey(): String
    suspend fun saveMarvelPrivateApiKey(privateKey: String)

    suspend fun getMarvelPublicApiKey(): String
    suspend fun saveMarvelPublicApiKey(publicKey: String)

}