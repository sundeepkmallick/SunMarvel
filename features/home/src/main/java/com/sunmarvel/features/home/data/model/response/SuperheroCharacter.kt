package com.sunmarvel.features.home.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SuperheroCharacter(
    val id: Long,
    val name: String,
    val slug: String = "",
    val biography: SuperheroBiography = SuperheroBiography(),
    val images: SuperheroImages = SuperheroImages(),
)

@Serializable
data class SuperheroBiography(
    val fullName: String = "",
    val alterEgos: String = "",
    val aliases: List<String> = emptyList(),
    val placeOfBirth: String = "",
    val firstAppearance: String = "",
    val publisher: String = "",
    val alignment: String = "",
)

@Serializable
data class SuperheroImages(
    val xs: String = "",
    val sm: String = "",
    val md: String = "",
    val lg: String = "",
)
