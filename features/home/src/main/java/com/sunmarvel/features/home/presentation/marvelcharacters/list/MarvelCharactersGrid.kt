package com.sunmarvel.features.home.presentation.marvelcharacters.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sunmarvel.features.home.data.model.response.Result
import com.sunmarvel.theme.tertiaryContainerLight

@Composable
fun GridMarvelCharacters(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    itemList: LazyPagingItems<Result>,
    showMarvelCharacterDetails: (Result) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        items(
            count = itemList.itemCount,
            //key = itemList.itemKey { it.id },
            //contentType = itemList.itemContentType { "Items" },
        ) { index ->
            val item = itemList[index]
            if (item != null) {
                GridItem(navController, item, showMarvelCharacterDetails)
            }
        }
    }
}

@Composable
fun GridItem(navController: NavHostController, item: Result, showMarvelCharacterDetails: (Result) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            showMarvelCharacterDetails(item)
            //navController.navigate(DetailsScreen.Overview.route)
        }
    ) {
        Column(
            modifier = Modifier
                .background(tertiaryContainerLight)
                .padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        item.thumbnail.path.replace(
                            "http://",
                            "https://"
                        ) + "/standard_xlarge." + item.thumbnail.extension
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(2.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.padding(2.dp),
                text = item.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

        }
    }
}
