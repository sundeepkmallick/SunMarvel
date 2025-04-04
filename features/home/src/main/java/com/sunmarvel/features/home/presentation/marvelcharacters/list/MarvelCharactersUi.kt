package com.sunmarvel.features.home.presentation.marvelcharacters.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.sunmarvel.features.home.data.model.response.Result
import com.sunmarvel.features.home.presentation.ProgressIndicator
import com.sunmarvel.features.home.presentation.SearchBox
import com.sunmarvel.theme.AppTheme
import com.sunmarvel.theme.ui.AppPreview

@Composable
fun MarvelCharactersScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: MarvelCharactersViewModel,
    showMarvelCharacterDetails: (Result) -> Unit
) {
    MarvelCharacters(navController, viewModel, showMarvelCharacterDetails)
}

@Composable
fun MarvelCharacters(
    navController: NavHostController,
    viewModel: MarvelCharactersViewModel,
    showMarvelCharacterDetails: (Result) -> Unit
) {

    val uiState = viewModel.uiState.collectAsState().value
    val searchBoxState = viewModel.searchBoxState.collectAsState().value
    val marvelCharacterList = viewModel.marvelCharacterList.collectAsLazyPagingItems()
    val inputText = viewModel.query.collectAsState().value
    val focusManager = LocalFocusManager.current
    val clearFocus = {
        focusManager.clearFocus()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) { innerPadding ->
        Modifier.padding(innerPadding)

        Column(
            modifier = Modifier.padding(
                top = 60.dp,
            )
        ) {
            SearchBox(inputText, { newInput -> viewModel.updateInput(newInput) })

            LaunchedEffect(uiState) {
                if (uiState == MarvelCharactersUiState.NONE) {
                    clearFocus()
                }
            }

            when {
                uiState == MarvelCharactersUiState.NONE -> {
                    //TODO
                }

                marvelCharacterList.loadState.refresh is LoadState.Error -> {
                    clearFocus()
                    val error = marvelCharacterList.loadState.refresh as LoadState.Error
                    Text(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                        ),
                        text = "${error.error.message}"
                    )
                }

                marvelCharacterList.loadState.refresh is LoadState.Loading -> {
                    ProgressIndicator()
                }

                else -> {
                    if (marvelCharacterList.itemSnapshotList.size == 0) {
                        clearFocus()
                    }
                    GridMarvelCharacters(
                        navController,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                        ),
                        marvelCharacterList,
                        showMarvelCharacterDetails
                    )
                }
            }
        }
    }
}


@AppPreview
@Composable
private fun MarvelCharactersPreview() {
    AppTheme {
        MarvelCharacters(
            rememberNavController(),
            hiltViewModel(),
            showMarvelCharacterDetails = {}
        )
    }
}