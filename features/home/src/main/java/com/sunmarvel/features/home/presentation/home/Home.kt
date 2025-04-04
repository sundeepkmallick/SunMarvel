package com.sunmarvel.features.home.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sunmarvel.features.home.navgraph.HomeNavGraph
import com.sunmarvel.theme.AppTheme
import com.sunmarvel.theme.ui.AppPreview

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Home(navController)
}

@Composable
fun Home(navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        //bottomBar = { BottomBar(navController = navController) },
    ) { innerPadding ->
        Modifier.padding(innerPadding)
        HomeNavGraph(
            navController = navController,
        )

        BottomBar(navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screens.MarvelCharacters,
        Screens.MarvelComics,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        /*NavigationBar {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }*/
        var tabIndex by remember { mutableIntStateOf(0) }
        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(selectedTabIndex = tabIndex, contentColor = MaterialTheme.colorScheme.primary) {
                screens.forEachIndexed { index, _ ->
                    Tab(
                        text = {
                            Text(
                                text = screens[index].title,
                                color = if (currentDestination?.hierarchy?.any { it.route == screens[index].route } == true) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.tertiary
                                }
                            )
                        },
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                            navController.navigate(screens[index].route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screens: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = screens.title)
        },
        icon = {
            Icon(
                imageVector = screens.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screens.route
        } == true,
        onClick = {
            navController.navigate(screens.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@AppPreview
@Composable
private fun HomePreview() {
    AppTheme {
        Home(rememberNavController())
    }
}