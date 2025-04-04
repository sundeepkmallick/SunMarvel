package com.sunmarvel.features.home.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marvel_characters")
data class MyFavoriteMarvelCharactersData(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "id")
    val id: String
)