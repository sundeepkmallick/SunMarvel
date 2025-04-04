package com.sunmarvel.features.home.presentation.marvelcharacters.details

import com.sunmarvel.features.home.data.model.response.Result

sealed class MarvelCharacterDetailsUiState {
    data object Loading: MarvelCharacterDetailsUiState()
    data class Error(val message: String): MarvelCharacterDetailsUiState()
    data class Loaded(val data: Result): MarvelCharacterDetailsUiState()
}
