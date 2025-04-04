package com.sunmarvel.di

import android.content.Context
import androidx.room.Room
import com.sunmarvel.features.home.data.database.MyFavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideMyFavoriteDatabase(@ApplicationContext context: Context): MyFavoriteDatabase {
        return Room.databaseBuilder(
            context,
            MyFavoriteDatabase::class.java,
            "my_favorite_db.db"
        ).build()
    }

    @Provides
    fun provideMyFavoriteDao(myFavoriteDatabase: MyFavoriteDatabase) = myFavoriteDatabase.myFavoriteMarvelCharactersDao

}