package com.sunmarvel.features.home.presentation.marvelcharacters.details

import com.sunmarvel.features.home.data.database.MyFavoriteMarvelCharactersData

sealed class MarvelCharacterDetailsModelEvent {
    data class OnFavoriteIconTapped(val myFavoriteMarvelCharactersData: MyFavoriteMarvelCharactersData, val isFavorite: Boolean) : MarvelCharacterDetailsModelEvent()
}