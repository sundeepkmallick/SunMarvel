package com.sunmarvel.storage

interface StorageHandler {
    suspend fun <T> save(key: String, value: T)
    suspend fun <T> read(key: String, defaultValue: T): T
    suspend fun delete(key: String)
    suspend fun clear()

    suspend fun contains(key: String): Boolean
    suspend fun rename(oldKey: String, newKey: String)
}