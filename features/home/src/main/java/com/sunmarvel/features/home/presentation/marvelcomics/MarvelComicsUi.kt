package com.sunmarvel.features.home.presentation.marvelcomics

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
import com.sunmarvel.features.home.presentation.ProgressIndicator
import com.sunmarvel.features.home.presentation.SearchBox
import com.sunmarvel.theme.AppTheme
import com.sunmarvel.theme.ui.AppPreview

@Composable
fun MarvelComicsScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: MarvelComicsViewModel
){
    MarvelComics(navController, viewModel)
}

@Composable
fun MarvelComics(
    navController: NavHostController = rememberNavController(),
    viewModel: MarvelComicsViewModel
){
    val uiState = viewModel.uiState.collectAsState().value
    val searchBoxState = viewModel.searchBoxState.collectAsState().value
    val marvelComicsList = viewModel.marvelComicsList.collectAsLazyPagingItems()
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

        Column (
            modifier = Modifier.padding(
                top = 60.dp,
            )
        ) {
            SearchBox(inputText, { newInput -> viewModel.updateInput(newInput) })

            LaunchedEffect(uiState) {
                if (uiState == MarvelComicsUiState.NONE) {
                    clearFocus()
                }
            }

            when {
                uiState == MarvelComicsUiState.NONE -> {
                    //TODO
                }

                marvelComicsList.loadState.refresh is LoadState.Error -> {
                    clearFocus()
                    val error = marvelComicsList.loadState.refresh as LoadState.Error
                    Text(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                        ),
                        text = "${error.error.message}"
                    )
                }

                marvelComicsList.loadState.refresh is LoadState.Loading -> {
                    ProgressIndicator()
                }

                else -> {
                    GridMarvelComics(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                        ),
                        marvelComicsList,
                    )
                }
            }
        }
    }
}

@AppPreview
@Composable
private fun MarvelComicsPreview() {
    AppTheme {
        MarvelComics(rememberNavController(), hiltViewModel())
    }
}