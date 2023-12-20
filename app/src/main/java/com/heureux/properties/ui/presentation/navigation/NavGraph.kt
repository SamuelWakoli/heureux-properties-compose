package com.heureux.properties.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.heureux.properties.ui.presentation.authgate.RegistrationScreen
import com.heureux.properties.ui.presentation.authgate.SignInScreen
import com.heureux.properties.ui.presentation.main.main_screen.MainScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    currentUserSignedIn: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = if (currentUserSignedIn) Screens.MainScreen.route else Screens.SignInScreen.route
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen()
        }
        composable(route = Screens.RegistrationScreen.route) {
            RegistrationScreen()
        }
        composable(route = Screens.MainScreen.route) {
            MainScreen()
        }
    }
}