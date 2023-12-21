package com.heureux.properties.ui.presentation.main.main_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.presentation.main.bottom_bar_destinations.BookmarksScreen
import com.heureux.properties.ui.presentation.main.bottom_bar_destinations.HomeScreen
import com.heureux.properties.ui.presentation.main.bottom_bar_destinations.MoreScreen
import com.heureux.properties.ui.presentation.main.bottom_bar_destinations.MyListingsScreen
import com.heureux.properties.ui.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainNavController: NavController) {
    val bottomNavController: NavHostController = rememberNavController()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            MainScreenAppbar(
                scrollBehavior = scrollBehavior,
                bottomNavController = bottomNavController,
                mainNavController = mainNavController,
            )
        },
        bottomBar = {
            MainScreenBottomBar(
                bottomNavController = bottomNavController,
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            composable(route = Screens.HomeScreen.route) {
                HomeScreen(scrollBehavior = scrollBehavior)
            }
            composable(route = Screens.BookmarksScreen.route) {
                BookmarksScreen(scrollBehavior = scrollBehavior)
            }
            composable(route = Screens.MyListingsScreen.route) {
                MyListingsScreen(scrollBehavior = scrollBehavior)
            }
            composable(route = Screens.MoreScreen.route) {
                MoreScreen(scrollBehavior = scrollBehavior)
            }

        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MaterialTheme {
        MainScreen(
            mainNavController = rememberNavController()
        )
    }
}