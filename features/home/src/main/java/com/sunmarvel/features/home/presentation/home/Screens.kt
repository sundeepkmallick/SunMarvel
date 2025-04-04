package com.sunmarvel.features.home.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    companion object {
        val MarvelCharacters = Screens("CHARACTERS", "Characters", Icons.Filled.Face)
        val MarvelComics = Screens("COMICS", "Comics", Icons.Filled.Star)
    }
}