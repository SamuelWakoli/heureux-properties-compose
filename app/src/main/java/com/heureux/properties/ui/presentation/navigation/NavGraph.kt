package com.heureux.properties.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavGraph(
    navController: NavHostController,
    currentUser: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = if (currentUser) Screens.HomeScreen.route else Screens.SignInScreen.route
    ) {

    }
}