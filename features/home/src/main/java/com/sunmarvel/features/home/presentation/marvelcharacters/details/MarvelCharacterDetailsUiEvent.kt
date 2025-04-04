package com.sunmarvel.features.home.presentation.marvelcharacters.details

sealed class MarvelCharacterDetailsUiEvent {
    data class OnFavoriteSaved(val id: String) : MarvelCharacterDetailsUiEvent()
    data class OnFavoriteDeleted(val id: String) : MarvelCharacterDetailsUiEvent()
    data class OnFavoriteStatusChanged(val isFavorite: Boolean) : MarvelCharacterDetailsUiEvent()
}