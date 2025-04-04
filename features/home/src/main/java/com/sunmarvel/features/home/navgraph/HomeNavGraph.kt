package com.sunmarvel.features.home.navgraph

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.sunmarvel.features.home.data.model.response.Result
import com.sunmarvel.features.home.presentation.home.Screens
import com.sunmarvel.features.home.presentation.marvelcharacters.details.MarvelCharacterDetailsScreen
import com.sunmarvel.features.home.presentation.marvelcharacters.list.MarvelCharactersScreen
import com.sunmarvel.features.home.presentation.marvelcomics.MarvelComicsScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = Screens.MarvelCharacters.route
    ) {
        composable(route = Screens.MarvelCharacters.route) {
            MarvelCharactersScreen(
                rememberNavController(),
                hiltViewModel(),
                showMarvelCharacterDetails = { result ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("marvel_character", result)
                    navController.navigate(DetailsScreen.Overview.route)
                })
            val context = LocalContext.current
            BackHandler {
                navController.navigateUp()
                (context as? Activity)?.finish()
            }
        }
        composable(route = Screens.MarvelComics.route) {
            MarvelComicsScreen(rememberNavController(), hiltViewModel())
        }

        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Overview.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            /*ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }*/
        }
        composable(route = DetailsScreen.Overview.route) {
            /*ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }*/
            val marvelCharacter: Result? = navController.previousBackStackEntry?.savedStateHandle?.get("marvel_character") // new

            MarvelCharacterDetailsScreen(
                navController,
                hiltViewModel(),
                marvelCharacter
            )
        }
    }
}

sealed class DetailsScreen(val route: String) {
    data object Information : DetailsScreen(route = "INFORMATION")
    data object Overview : DetailsScreen(route = "OVERVIEW")
}

object Graph {
    const val HOME = "home"
    const val DETAILS = "details"
}