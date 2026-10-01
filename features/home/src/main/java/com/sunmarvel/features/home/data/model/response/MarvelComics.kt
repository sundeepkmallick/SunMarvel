package com.sunmarvel.features.home.data.model.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class MarvelComics(
    val code: Long = 200,
    val status: String = "OK",
    val copyright: String = "",
    val attributionText: String = "Data provided by Open Library",
    val attributionHTML: String = "",
    val etag: String = "",
    val data: ComicsData = ComicsData(),
)

@Serializable
data class ComicsData(
    val offset: Long = 0,
    val limit: Long = 20,
    val total: Long = 0,
    val count: Long = 0,
    val results: List<ComicsResult> = emptyList(),
)

@Serializable
data class ComicsResult(
    val id: Long = 0,
    val digitalId: Long = 0,
    val title: String = "",
    val issueNumber: Long = 0,
    val variantDescription: String = "",
    val description: String? = null,
    val modified: String = "",
    val isbn: String = "",
    val upc: String = "",
    val diamondCode: String = "",
    val ean: String = "",
    val issn: String = "",
    val format: String = "",
    val pageCount: Long = 0,
    val textObjects: List<TextObject> = emptyList(),
    val resourceURI: String = "",
    val urls: List<ComicsResultUrl> = emptyList(),
    val series: ComicsSeries = ComicsSeries(),
    val variants: List<Variant> = emptyList(),
    val collections: List<Collection> = emptyList(),
    val collectedIssues: List<CollectedIssue> = emptyList(),
    val dates: List<Date> = emptyList(),
    val prices: List<Price> = emptyList(),
    val thumbnail: ComicsThumbnail = ComicsThumbnail(),
    val images: List<Image> = emptyList(),
    val creators: Creators = Creators(),
    val characters: Characters = Characters(),
    val stories: ComicsStories = ComicsStories(),
    val events: ComicsEvents = ComicsEvents(),
)

@Serializable
data class TextObject(
    val type: String,
    val language: String,
    val text: String,
)

@Serializable
data class ComicsResultUrl(
    val type: String,
    val url: String,
)

@Serializable
data class ComicsSeries(
    val resourceURI: String,
    val name: String,
)

@Serializable
data class Variant(
    val resourceURI: String,
    val name: String,
)

@Serializable
data class Collection(
    val resourceURI: String,
    val name: String,
)

@Serializable
data class CollectedIssue(
    val resourceURI: String,
    val name: String,
)

@Serializable
data class Date(
    val type: String,
    val date: String,
)

@Serializable
data class Price(
    val type: String,
    val price: Double,
)

@Serializable
data class ComicsThumbnail(
    val path: String,
    val extension: String,
)

@Serializable
data class Image(
    val path: String,
    val extension: String,
)

@Serializable
data class Creators(
    val available: Long,
    val collectionURI: String,
    val items: List<CreatorItem>,
    val returned: Long,
)

@Serializable
data class CreatorItem(
    val resourceURI: String,
    val name: String,
    val role: String,
)

@Serializable
data class Characters(
    val available: Long,
    val collectionURI: String,
    val items: List<CharacterItem>,
    val returned: Long,
)

@Serializable
data class CharacterItem(
    val resourceURI: String,
    val name: String,
)

@Serializable
data class ComicsStories(
    val available: Long,
    val collectionURI: String,
    val items: List<Item3>,
    val returned: Long,
)

@Serializable
data class Item3(
    val resourceURI: String,
    val name: String,
    val type: String,
)

@Serializable
data class ComicsEvents(
    val available: Long,
    val collectionURI: String,
    val items: JsonArray,
    val returned: Long,
)
