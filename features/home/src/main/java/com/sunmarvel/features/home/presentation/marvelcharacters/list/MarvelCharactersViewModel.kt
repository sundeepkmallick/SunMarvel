package com.sunmarvel.features.home.presentation.marvelcharacters.list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sunmarvel.features.home.domain.MarvelUseCase
import com.sunmarvel.features.home.paging.MarvelCharactersPagingSource
import com.sunmarvel.features.home.presentation.SearchBoxUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val PAGE_SIZE = 1

@HiltViewModel
class MarvelCharactersViewModel @Inject constructor(
    private val marvelUseCase: MarvelUseCase,
) : ViewModel() {
    private val _searchBoxState: MutableStateFlow<SearchBoxUiState> =
        MutableStateFlow(SearchBoxUiState.IDLE)
    val searchBoxState: StateFlow<SearchBoxUiState> = _searchBoxState

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _uiState: MutableStateFlow<MarvelCharactersUiState> = MutableStateFlow(
        MarvelCharactersUiState.NONE
    )
    val uiState: StateFlow<MarvelCharactersUiState> = _uiState

    private fun getMarvelCharacters(query: String) = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        MarvelCharactersPagingSource(query, marvelUseCase)
    }.flow

    fun updateInput(inputText: String) {
        _query.update { inputText }
        _searchBoxState.update { SearchBoxUiState.INPUT }
    }

    fun clearInput() {
        _uiState.tryEmit(MarvelCharactersUiState.NONE)
        _query.update { "" }
        _searchBoxState.update { SearchBoxUiState.EMPTY }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val marvelCharacterList = query.debounce(timeoutMillis = 500).flatMapLatest { input ->
        if (input.isBlank() || input.isEmpty()) {
            _uiState.tryEmit(MarvelCharactersUiState.NONE)
            //return@flatMapLatest flowOf(PagingData.empty())
            getMarvelCharacters(input)
        }

        _uiState.tryEmit(MarvelCharactersUiState.LOADED)
        getMarvelCharacters(input)
    }

}