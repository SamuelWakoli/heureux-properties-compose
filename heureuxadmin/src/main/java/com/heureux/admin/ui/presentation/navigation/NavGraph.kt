package com.heureux.admin.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.heureux.admin.ui.presentation.authgate.AuthViewModel
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreen
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreenViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    currentUserSignedIn: Boolean,
    authViewModel: AuthViewModel,
    mainScreenViewModel: MainScreenViewModel,
    onSignInWithGoogle: () -> Unit,
) {


    NavHost(
        navController = navController,
        startDestination =  Screens.MainScreen.route
    ) {
//        composable(route = Screens.SignInScreen.route) {
//            SignInScreen(navController = navController,
//                viewModel = authViewModel,
//                onSignInWithGoogle = { onSignInWithGoogle() })
//        }
//        composable(route = Screens.RegistrationScreen.route) {
//            RegistrationScreen(navController = navController,
//                viewModel = authViewModel,
//                onSignInWithGoogle = { onSignInWithGoogle() })
//        }
//        composable(route = Screens.ForgotPasswordScreen.route) {
//            ForgotPasswordScreen(
//                navController = navController,
//                viewModel = authViewModel,
//            )
//        }
        composable(route = Screens.MainScreen.route) {
            MainScreen(
                navController = navController,
                viewModel = mainScreenViewModel,
            )
        }
    }
}