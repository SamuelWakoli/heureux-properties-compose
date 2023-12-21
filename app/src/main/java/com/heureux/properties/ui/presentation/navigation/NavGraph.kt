package com.heureux.properties.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.heureux.properties.ui.presentation.authgate.RegistrationScreen
import com.heureux.properties.ui.presentation.authgate.SignInScreen
import com.heureux.properties.ui.presentation.main.about_us_screen.AboutUsScreen
import com.heureux.properties.ui.presentation.main.contact_us_screen.ContactUsScreen
import com.heureux.properties.ui.presentation.main.feedback_screen.FeedbackScreen
import com.heureux.properties.ui.presentation.main.filter_results_screen.FilterResultsScreen
import com.heureux.properties.ui.presentation.main.filter_screen.FilterScreen
import com.heureux.properties.ui.presentation.main.main_screen.MainScreen
import com.heureux.properties.ui.presentation.main.my_properties_screen.MyPropertiesScreen
import com.heureux.properties.ui.presentation.main.payment_history_screen.PaymentHistoryScreen
import com.heureux.properties.ui.presentation.main.profile_screen.ProfileScreen
import com.heureux.properties.ui.presentation.viewmodels.AppViewModelProvider
import com.heureux.properties.ui.presentation.viewmodels.AuthViewModel
import com.heureux.properties.ui.presentation.viewmodels.MainScreenViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    currentUserSignedIn: Boolean,
) {

    val authViewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val mainScreenViewModel: MainScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = if (currentUserSignedIn) Screens.MainScreen.route else Screens.SignInScreen.route
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(
                mainNavController = navController,
//                viewModel = authViewModel,
            )
        }
        composable(route = Screens.RegistrationScreen.route) {
            RegistrationScreen(
                mainNavController = navController,
//                viewModel = authViewModel,
            )
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
            FilterScreen(mainNavController = navController)
        }
        composable(route = Screens.FilterResultsScreen.route) {
            FilterResultsScreen(mainNavController = navController)
        }
        composable(route = Screens.FeedbackScreen.route) {
            FeedbackScreen(mainNavController = navController)
        }
        composable(route = Screens.MyPropertiesScreen.route) {
            MyPropertiesScreen(mainNavController = navController)
        }
        composable(route = Screens.PaymentHistoryScreen.route) {
            PaymentHistoryScreen(mainNavController = navController)
        }
    }
}