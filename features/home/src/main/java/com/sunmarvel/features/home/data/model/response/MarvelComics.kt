package com.sunmarvel.features.home.data.model.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class MarvelComics(
    val code: Long,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val etag: String,
    val data: ComicsData,
)

@Serializable
data class ComicsData(
    val offset: Long,
    val limit: Long,
    val total: Long,
    val count: Long,
    val results: List<ComicsResult>,
)

@Serializable
data class ComicsResult(
    val id: Long,
    val digitalId: Long,
    val title: String,
    val issueNumber: Long,
    val variantDescription: String,
    val description: String?,
    val modified: String,
    val isbn: String,
    val upc: String,
    val diamondCode: String,
    val ean: String,
    val issn: String,
    val format: String,
    val pageCount: Long,
    val textObjects: List<TextObject>,
    val resourceURI: String,
    val urls: List<ComicsResultUrl>,
    val series: ComicsSeries,
    val variants: List<Variant>,
    val collections: List<Collection>,
    val collectedIssues: List<CollectedIssue>,
    val dates: List<Date>,
    val prices: List<Price>,
    val thumbnail: ComicsThumbnail,
    val images: List<Image>,
    val creators: Creators,
    val characters: Characters,
    val stories: ComicsStories,
    val events: ComicsEvents,
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
