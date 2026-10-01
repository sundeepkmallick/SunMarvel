package com.sunmarvel.features.home.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacters(
    val code: Long = 200,
    val status: String = "OK",
    val copyright: String = "",
    val attributionText: String = "Data provided by Superhero API",
    val attributionHTML: String = "",
    val etag: String = "",
    val data: Data = Data(),
)

@Serializable
data class Data(
    val offset: Long = 0,
    val limit: Long = 20,
    val total: Long = 0,
    val count: Long = 0,
    val results: List<Result> = emptyList(),
)

@Serializable
@Parcelize
data class Result(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val modified: String = "",
    val thumbnail: Thumbnail = Thumbnail(),
    val resourceURI: String = "",
    val comics: Comics = Comics(),
    val series: Series = Series(),
    val stories: Stories = Stories(),
    val events: Events = Events(),
    val urls: List<Url> = emptyList(),
): Parcelable

@Serializable
@Parcelize
data class Thumbnail(
    val path: String = "",
    val extension: String = "",
): Parcelable

@Serializable
@Parcelize
data class Comics(
    val available: Long = 0,
    val collectionURI: String = "",
    val items: List<Item> = emptyList(),
    val returned: Long = 0,
): Parcelable

@Serializable
@Parcelize
data class Item(
    val resourceURI: String = "",
    val name: String = "",
): Parcelable

@Serializable
@Parcelize
data class Series(
    val available: Long = 0,
    val collectionURI: String = "",
    val items: List<Item> = emptyList(),
    val returned: Long = 0,
): Parcelable

@Serializable
@Parcelize
data class Stories(
    val available: Long = 0,
    val collectionURI: String = "",
    val items: List<StoryItem> = emptyList(),
    val returned: Long = 0,
): Parcelable

@Serializable
@Parcelize
data class StoryItem(
    val resourceURI: String = "",
    val name: String = "",
    val type: String = "",
): Parcelable

@Serializable
@Parcelize
data class Events(
    val available: Long = 0,
    val collectionURI: String = "",
    val items: List<Item> = emptyList(),
    val returned: Long = 0,
): Parcelable

@Serializable
@Parcelize
data class Url(
    val type: String = "",
    val url: String = "",
): Parcelable
