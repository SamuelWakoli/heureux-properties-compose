package com.heureux.properties.ui.presentation.screens.main_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.AppViewModelProvider
import com.heureux.properties.ui.presentation.navigation.Screens
import com.heureux.properties.ui.presentation.screens.main_screen.bottom_bar_destinations.BookmarksScreen
import com.heureux.properties.ui.presentation.screens.main_screen.bottom_bar_destinations.HomeScreen
import com.heureux.properties.ui.presentation.screens.main_screen.bottom_bar_destinations.MoreScreen
import com.heureux.properties.ui.presentation.screens.main_screen.bottom_bar_destinations.MyListingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel,
) {
    val bottomNavController: NavHostController = rememberNavController()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val userData = viewModel.userProfileData.collectAsState().value

    Scaffold(topBar = {
        MainScreenAppbar(
            userData = userData,
            scrollBehavior = scrollBehavior,
            bottomNavController = bottomNavController,
            mainNavController = navController,
        )
    }, bottomBar = {
        MainScreenBottomBar(
            bottomNavController = bottomNavController,
        )
    },
        floatingActionButton = {
            if (bottomNavController.currentBackStackEntryAsState().value?.destination?.route == Screens.MyListingsScreen.route)
                FloatingActionButton(onClick = {
                    navController.navigate(Screens.AddPropertyScreen.route) {
                        launchSingleTop = true
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add property listing",
                    )
                }
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
                HomeScreen(
                    navController = navController, scrollBehavior = scrollBehavior,
                    viewModel = viewModel,
                )
            }
            composable(route = Screens.BookmarksScreen.route) {
                BookmarksScreen(
                    navController = navController, scrollBehavior = scrollBehavior,
                    viewModel = viewModel,
                )
            }
            composable(route = Screens.MyListingsScreen.route) {
                MyListingsScreen(
                    navController = navController, scrollBehavior = scrollBehavior,
                    viewModel = viewModel
                )
            }
            composable(route = Screens.MoreScreen.route) {
                MoreScreen(
                    scrollBehavior = scrollBehavior,
                    viewModel = viewModel(factory = AppViewModelProvider.Factory),
                    mainNavController = navController,
                )
            }

        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MaterialTheme {
        MainScreen(
            navController = rememberNavController(),
            viewModel = viewModel(factory = AppViewModelProvider.Factory)
        )
    }
}