package com.sunmarvel.data.repository

import com.sunmarvel.storage.sharedpreference.EncryptedSharedPreference
import javax.inject.Inject

class AppRepositoryImpl@Inject constructor(
    private val encryptedSharedPreference: EncryptedSharedPreference,
): AppRepository{
    override suspend fun getMarvelPrivateApiKey(): String {
        return encryptedSharedPreference.read("mavenPrivateKey", "")
    }

    override suspend fun saveMarvelPrivateApiKey(privateKey: String) {
        encryptedSharedPreference.save("mavenPrivateKey", privateKey)
    }

    override suspend fun getMarvelPublicApiKey(): String {
        return encryptedSharedPreference.read("mavenPublicKey", "")
    }

    override suspend fun saveMarvelPublicApiKey(publicKey: String) {
        encryptedSharedPreference.save("mavenPublicKey", publicKey)
    }
}