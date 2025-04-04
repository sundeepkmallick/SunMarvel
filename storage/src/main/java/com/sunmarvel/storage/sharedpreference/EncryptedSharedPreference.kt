package com.sunmarvel.storage.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.sunmarvel.storage.StorageHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EncryptedSharedPreference @Inject constructor(@ApplicationContext private val context: Context): StorageHandler {

    private var mainKeyAlias: String
    private val fileToReadWrite = "sunmarvel-secured-data.txt"
    private val sharedPreferences: SharedPreferences

    init {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        sharedPreferences = EncryptedSharedPreferences.create(
            fileToReadWrite,
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    override suspend fun <T> save(key: String, value: T) {
        if (value is String) {
            saveToSharedPreferences(key, value)
        } else if (value is Int) {
            saveToSharedPreferences(key, value)
        }
    }

    private fun saveToSharedPreferences(key: String, value: String){
        with (sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    private fun saveToSharedPreferences(key: String, value: Int){
        with (sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
    }

    override suspend fun <T> read(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> {
                readStringFromSharedPreferences(key) as T
            }

            is Int -> {
                readIntFromSharedPreferences(key) as T
            }

            else -> {
                defaultValue
            }
        }
    }

    private fun readStringFromSharedPreferences(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    private fun readIntFromSharedPreferences(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    override suspend fun delete(key: String) {
        with (sharedPreferences.edit()) {
            remove(key)
            apply()
        }
    }

    override suspend fun clear() {
        sharedPreferences.all.clear()
    }

    override suspend fun contains(key: String): Boolean {
       return sharedPreferences.contains(key)
    }

    override suspend fun rename(oldKey: String, newKey: String) {
        sharedPreferences.edit().run {
            putString(newKey, sharedPreferences.getString(oldKey, ""))
            remove(oldKey)
            apply()
        }
    }
}