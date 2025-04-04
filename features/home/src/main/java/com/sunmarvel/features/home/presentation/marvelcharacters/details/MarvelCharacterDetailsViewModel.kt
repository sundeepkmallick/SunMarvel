package com.sunmarvel.features.home.presentation.marvelcharacters.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunmarvel.features.home.data.database.MyFavoriteMarvelCharactersData
import com.sunmarvel.features.home.domain.MarvelUseCase
import com.sunmarvel.features.home.domain.MyFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarvelCharacterDetailsViewModel @Inject constructor(
    private val myFavoriteUseCase: MyFavoriteUseCase
) : ViewModel() {
    private val _favoriteList = MutableStateFlow<List<MyFavoriteMarvelCharactersData>>(emptyList())
    val favoriteList: StateFlow<List<MyFavoriteMarvelCharactersData>> = _favoriteList

    init {
        getMyFavoriteMarvelCharacters()
    }

    private fun getMyFavoriteMarvelCharacters() = viewModelScope.launch {
        _favoriteList.value = myFavoriteUseCase.invokeGetMyFavoriteMarvelCharacters()
    }

    private fun getMyFavoriteMarvelCharacter(id: String) = viewModelScope.launch {
        myFavoriteUseCase.invokeGetMyFavoriteMarvelCharactersById(id)
    }

    private fun upsertMyFavoriteMarvelCharacter(myFavoriteMarvelCharactersData: MyFavoriteMarvelCharactersData) =
        viewModelScope.launch {
            myFavoriteUseCase.invokeUpsert(myFavoriteMarvelCharactersData)
        }

    private fun deleteMyFavoriteMarvelCharacter(id: String) = viewModelScope.launch {
        myFavoriteUseCase.invokeDeleteMyFavoriteMarvelCharactersById(id)
    }

    fun onEvent(event: MarvelCharacterDetailsModelEvent) {
        when (event) {
            is MarvelCharacterDetailsModelEvent.OnFavoriteIconTapped -> {
                if (event.isFavorite) {
                    upsertMyFavoriteMarvelCharacter(event.myFavoriteMarvelCharactersData)
                } else {
                    deleteMyFavoriteMarvelCharacter(event.myFavoriteMarvelCharactersData.id)
                }
                getMyFavoriteMarvelCharacters()
            }
        }
    }
}
