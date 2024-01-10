package com.heureux.admin.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.heureux.admin.ui.AppViewModelProvider
import com.heureux.admin.ui.presentation.authgate.AuthViewModel
import com.heureux.admin.ui.presentation.authgate.ForgotPasswordScreen
import com.heureux.admin.ui.presentation.authgate.RegistrationScreen
import com.heureux.admin.ui.presentation.authgate.SignInScreen
import com.heureux.admin.ui.presentation.screens.add_property_screen.AddPropertyImagesScreen
import com.heureux.admin.ui.presentation.screens.add_property_screen.AddPropertyScreen
import com.heureux.admin.ui.presentation.screens.add_property_screen.AddPropertyScreenViewModel
import com.heureux.admin.ui.presentation.screens.administration_screen.AdministrationScreen
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreen
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreenViewModel
import com.heureux.admin.ui.presentation.screens.profile_screen.ProfileScreen
import com.heureux.admin.ui.presentation.screens.profile_screen.ProfileScreenViewModel
import com.heureux.admin.ui.presentation.screens.property_details_screen.PropertyDetailsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    currentUserSignedIn: Boolean,
    authViewModel: AuthViewModel,
    mainScreenViewModel: MainScreenViewModel,
    onSignInWithGoogle: () -> Unit,
) {

    val profileScreenViewModel =
        viewModel<ProfileScreenViewModel>(factory = AppViewModelProvider.Factory)

    val addPropertyScreenViewModel =
        viewModel<AddPropertyScreenViewModel>(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = if (currentUserSignedIn) Screens.MainScreen.route else Screens.SignInScreen.route
    ) {
        composable(Screens.SignInScreen.route) {
            SignInScreen(
                navController = navController,
                viewModel = authViewModel,
                onSignInWithGoogle = { onSignInWithGoogle() },
            )
        }
        composable(Screens.RegistrationScreen.route) {
            RegistrationScreen(
                navController = navController,
                viewModel = authViewModel,
                onSignInWithGoogle = { onSignInWithGoogle() },
            )
        }
        composable(Screens.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(
                navController = navController,
                viewModel = authViewModel,
            )
        }
        composable(Screens.MainScreen.route) {
            MainScreen(
                navController = navController,
                viewModel = mainScreenViewModel,
            )
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(
                navController = navController,
                viewModel = profileScreenViewModel
            )
        }
        composable(Screens.AdministrationScreen.route) {
            AdministrationScreen(
                navController = navController,
                viewModel = profileScreenViewModel
            )
        }
        composable(Screens.PropertyDetailsScreen.route) {
            PropertyDetailsScreen(
                navController = navController,
                viewModel = mainScreenViewModel,
            )
        }
        composable(Screens.AddPropertyScreen.route) {
            AddPropertyScreen(
                navController = navController,
                viewModel = addPropertyScreenViewModel,
            )
        }
        composable(Screens.AddPropertyImagesScreen.route) {
            AddPropertyImagesScreen(
                navController = navController,
                viewModel = addPropertyScreenViewModel,
            )
        }
    }
}