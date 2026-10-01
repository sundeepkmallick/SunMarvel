package com.sunmarvel.features.home.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenLibrarySearch(
    val start: Int = 0,
    @SerialName("numFound") val numFound: Int = 0,
    val docs: List<OpenLibraryBook> = emptyList(),
)

@Serializable
data class OpenLibraryBook(
    val key: String = "",
    val title: String = "",
    @SerialName("cover_i") val coverId: Int? = null,
    @SerialName("first_publish_year") val firstPublishYear: Int? = null,
    @SerialName("author_name") val authorNames: List<String> = emptyList(),
)
