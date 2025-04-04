package com.sunmarvel.features.home.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MyFavoriteMarvelCharactersData::class],
    version = 1,
    exportSchema = false
)
abstract class MyFavoriteDatabase : RoomDatabase() {
    abstract val myFavoriteMarvelCharactersDao: MyFavoriteMarvelCharactersDao
}