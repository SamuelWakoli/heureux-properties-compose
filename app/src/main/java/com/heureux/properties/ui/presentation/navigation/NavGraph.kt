package com.heureux.properties.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.heureux.properties.ui.presentation.authgate.RegistrationScreen
import com.heureux.properties.ui.presentation.authgate.SignInScreen
import com.heureux.properties.ui.presentation.main.about_us_screen.AboutUsScreen
import com.heureux.properties.ui.presentation.main.contact_us_screen.ContactUsScreen
import com.heureux.properties.ui.presentation.main.filter_results_screen.FilterResultsScreen
import com.heureux.properties.ui.presentation.main.filter_screen.FilterScreen
import com.heureux.properties.ui.presentation.main.main_screen.MainScreen
import com.heureux.properties.ui.presentation.main.profile_screen.ProfileScreen

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
            MainScreen(
                mainNavController = navController
            )
        }

        // other screens
        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen(mainNavController = navController)
        }
        composable(route = Screens.AboutUsScreen.route) {
            AboutUsScreen(mainNavController = navController)
        }
        composable(route = Screens.ContactUsScreen.route) {
            ContactUsScreen(mainNavController = navController)
        }
        composable(route = Screens.FilterScreen.route) {
            FilterScreen()
        }
        composable(route = Screens.FilterResultsScreen.route) {
            FilterResultsScreen()
        }
    }
}