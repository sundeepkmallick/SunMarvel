package com.sunmarvel.features.home.presentation.marvelcharacters.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sunmarvel.features.home.R
import com.sunmarvel.features.home.data.database.MyFavoriteMarvelCharactersData
import com.sunmarvel.features.home.data.model.response.Comics
import com.sunmarvel.features.home.data.model.response.Result
import com.sunmarvel.theme.AppTheme
import com.sunmarvel.theme.onBackgroundDark
import com.sunmarvel.theme.onPrimaryLight
import com.sunmarvel.theme.onTertiaryContainerLightMediumContrast
import com.sunmarvel.theme.primaryLight
import com.sunmarvel.theme.tertiaryContainerLightMediumContrast
import com.sunmarvel.theme.ui.AppPreview

@Composable
fun MarvelCharacterDetailsScreen(
    navController: NavHostController,
    viewModel: MarvelCharacterDetailsViewModel = hiltViewModel(),
    marvelCharacter: Result?,
) {
    MarvelCharacterDetails(navController, viewModel, marvelCharacter)
}

@Composable
fun MarvelCharacterDetails(
    navController: NavHostController,
    viewModel: MarvelCharacterDetailsViewModel = hiltViewModel(),
    marvelCharacter: Result?,
) {
    val favoriteList = viewModel.favoriteList.collectAsState().value
    val myFavoriteMarvelCharactersData = MyFavoriteMarvelCharactersData(
        name = marvelCharacter?.name.toString(),
        id = marvelCharacter?.id.toString()
    )
    val index = favoriteList.indexOfFirst { it.id == marvelCharacter?.id.toString() }
    val isFavorite = index >= 0

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryLight.copy(
                        alpha = 0f
                    )
                ),
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = onPrimaryLight)
                    }
                }, actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(MarvelCharacterDetailsModelEvent.OnFavoriteIconTapped(myFavoriteMarvelCharactersData, !isFavorite))
                    }) {
                        if (isFavorite) {
                            Icon(Icons.Filled.Favorite, null, tint = primaryLight)
                        } else {
                            Icon(Icons.Filled.Favorite, null, tint = onPrimaryLight)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Modifier.padding(innerPadding)

        Box {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (marvelCharacter == null) {
                    Text(text = stringResource(R.string.no_details_found))
                } else {
                    Box(
                        modifier = Modifier
                            .weight(0.4f)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(
                                    marvelCharacter.thumbnail.path.replace(
                                        "http://",
                                        "https://"
                                    ) + "/standard_xlarge." + marvelCharacter.thumbnail.extension
                                )
                                .crossfade(true)
                                .build(),
                            contentDescription = marvelCharacter.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(2.dp)),
                            contentScale = ContentScale.FillWidth
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            onTertiaryContainerLightMediumContrast.copy(alpha = 0.2f),
                                            tertiaryContainerLightMediumContrast.copy(alpha = 1f)
                                        )
                                    )
                                ),
                        ) {
                            Text(
                                text = marvelCharacter.name,
                                color = onPrimaryLight,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.BottomStart),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            )
                        }

                    }
                    Box(
                        modifier = Modifier
                            .weight(0.8f)
                            .fillMaxWidth()
                            .background(onBackgroundDark)
                    ) {
                        Column {
                            Text(
                                text = stringResource(R.string.title_comics),
                                modifier = Modifier
                                    .padding(
                                        top = 10.dp,
                                        start = 16.dp,
                                        end = 10.dp
                                    )
                                    .align(Alignment.Start),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                            )
                            ComicsList(marvelCharacter.comics)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComicsList(comics: Comics) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        items(comics.items.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = comics.items[index].name,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Go to comic details",
                )
            }
            if (index < comics.items.size - 1) {
                HorizontalDivider(
                    color = onBackgroundDark
                )
            }
        }
    }
}

@AppPreview
@Composable
private fun MarvelCharacterDetailsPreview() {
    AppTheme {
        MarvelCharacterDetails(
            rememberNavController(),
            hiltViewModel(),
            marvelCharacter = null
        )
    }
}