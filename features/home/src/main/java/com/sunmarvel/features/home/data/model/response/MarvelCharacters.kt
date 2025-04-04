package com.sunmarvel.features.home.data.model.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacters(
    val code: Long,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val etag: String,
    val data: Data,
)

@Serializable
data class Data(
    val offset: Long,
    val limit: Long,
    val total: Long,
    val count: Long,
    val results: List<Result>,
)

@Serializable
@Parcelize
data class Result(
    val id: Long,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Thumbnail,
    val resourceURI: String,
    val comics: Comics,
    val series: Series,
    val stories: Stories,
    val events: Events,
    val urls: List<Url>,
): Parcelable

@Serializable
@Parcelize
data class Thumbnail(
    val path: String,
    val extension: String,
): Parcelable

@Serializable
@Parcelize
data class Comics(
    val available: Long,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Long,
): Parcelable

@Serializable
@Parcelize
data class Item(
    val resourceURI: String,
    val name: String,
): Parcelable

@Serializable
@Parcelize
data class Series(
    val available: Long,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Long,
): Parcelable

@Serializable
@Parcelize
data class Stories(
    val available: Long,
    val collectionURI: String,
    val items: List<StoryItem>,
    val returned: Long,
): Parcelable

@Serializable
@Parcelize
data class StoryItem(
    val resourceURI: String,
    val name: String,
    val type: String,
): Parcelable

@Serializable
@Parcelize
data class Events(
    val available: Long,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Long,
): Parcelable

@Serializable
@Parcelize
data class Url(
    val type: String,
    val url: String,
): Parcelable