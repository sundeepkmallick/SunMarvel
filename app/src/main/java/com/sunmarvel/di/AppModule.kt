package com.sunmarvel.di

import android.content.Context
import androidx.room.Room
import com.sunmarvel.data.repository.AppRepository
import com.sunmarvel.data.repository.AppRepositoryImpl
import com.sunmarvel.features.home.data.database.MyFavoriteDatabase
import com.sunmarvel.features.home.data.repository.MarvelRepository
import com.sunmarvel.features.home.data.repository.MarvelRepositoryImpl
import com.sunmarvel.features.home.data.repository.MyFavoriteRepository
import com.sunmarvel.features.home.data.repository.MyFavoriteRepositoryImpl
import com.sunmarvel.storage.StorageHandler
import com.sunmarvel.storage.sharedpreference.EncryptedSharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
class AppModule {
    @Provides
    fun provideMarvelRepository(impl: MarvelRepositoryImpl): MarvelRepository = impl

    @Provides
    fun provideAppRepository(impl: AppRepositoryImpl): AppRepository = impl

    @Provides
    fun provideEncryptedSharedPreferences(impl: EncryptedSharedPreference): StorageHandler = impl

    @Provides
    fun provideMyFavoriteRepository(impl: MyFavoriteRepositoryImpl): MyFavoriteRepository = impl

}