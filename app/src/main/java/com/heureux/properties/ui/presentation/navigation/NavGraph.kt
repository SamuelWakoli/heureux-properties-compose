package com.heureux.properties.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.heureux.properties.ui.AppViewModelProvider
import com.heureux.properties.ui.presentation.authgate.AuthViewModel
import com.heureux.properties.ui.presentation.authgate.ForgotPasswordScreen
import com.heureux.properties.ui.presentation.authgate.RegistrationScreen
import com.heureux.properties.ui.presentation.authgate.SignInScreen
import com.heureux.properties.ui.presentation.screens.about_us_screen.AboutUsScreen
import com.heureux.properties.ui.presentation.screens.contact_us_screen.ContactUsScreen
import com.heureux.properties.ui.presentation.screens.edit_profile_screen.EditProfileScreen
import com.heureux.properties.ui.presentation.screens.feedback_screen.FeedbackScreen
import com.heureux.properties.ui.presentation.screens.filter_results_screen.FilterResultsScreen
import com.heureux.properties.ui.presentation.screens.filter_screen.FilterScreen
import com.heureux.properties.ui.presentation.screens.inquires_screen.MyInquiresScreen
import com.heureux.properties.ui.presentation.screens.main_screen.MainScreen
import com.heureux.properties.ui.presentation.screens.main_screen.MainScreenViewModel
import com.heureux.properties.ui.presentation.screens.my_properties_screen.MyPropertiesScreen
import com.heureux.properties.ui.presentation.screens.notifications_screen.NotificationsScreen
import com.heureux.properties.ui.presentation.screens.payment_history_screen.PaymentHistoryScreen
import com.heureux.properties.ui.presentation.screens.profile_screen.ProfileScreen
import com.heureux.properties.ui.presentation.screens.inquiry_screen.InquiryScreen
import com.heureux.properties.ui.presentation.screens.property_details_screen.PropertyDetailsScreen
import com.heureux.properties.ui.presentation.screens.sell_with_us_screen.AddPropertyImagesScreen
import com.heureux.properties.ui.presentation.screens.sell_with_us_screen.SellWithUsScreen
import com.heureux.properties.ui.presentation.screens.sell_with_us_screen.SellWithUsViewModel
import com.heureux.properties.ui.presentation.screens.sold_properties_screen.SoldPropertiesScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    currentUserSignedIn: Boolean,
    authViewModel: AuthViewModel,
    mainScreenViewModel: MainScreenViewModel,
    onSignInWithGoogle: () -> Unit,
) {

    val sellWithUsViewModel = viewModel<SellWithUsViewModel>(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = if (currentUserSignedIn) Screens.MainScreen.route else Screens.SignInScreen.route
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController = navController,
                viewModel = authViewModel,
                onSignInWithGoogle = { onSignInWithGoogle() })
        }
        composable(route = Screens.RegistrationScreen.route) {
            RegistrationScreen(navController = navController,
                viewModel = authViewModel,
                onSignInWithGoogle = { onSignInWithGoogle() })
        }
        composable(route = Screens.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(
                navController = navController,
                viewModel = authViewModel,
            )
        }
        composable(route = Screens.MainScreen.route) {
            MainScreen(
                navController = navController,
                viewModel = mainScreenViewModel,
            )
        }

        // other screens
        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen(
                navController = navController,
                profileScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
            )
        }
        composable(route = Screens.EditProfileScreen.route) {
            EditProfileScreen(
                navController = navController,
                viewModel = viewModel(factory = AppViewModelProvider.Factory),
            )
        }
        composable(route = Screens.AboutUsScreen.route) {
            AboutUsScreen(navController = navController)
        }
        composable(route = Screens.ContactUsScreen.route) {
            ContactUsScreen(navController = navController)
        }
        composable(route = Screens.FilterScreen.route) {
            FilterScreen(navController = navController)
        }
        composable(route = Screens.FilterResultsScreen.route) {
            FilterResultsScreen(navController = navController)
        }
        composable(route = Screens.FeedbackScreen.route) {
            FeedbackScreen(
                navController = navController,
                viewModel = mainScreenViewModel
            )
        }
        composable(route = Screens.MyPropertiesScreen.route) {
            MyPropertiesScreen(
                navController = navController,
                viewModel = viewModel(factory = AppViewModelProvider.Factory)
            )
        }
        composable(route = Screens.PaymentHistoryScreen.route) {
            PaymentHistoryScreen(
                navController = navController,
                viewModel = viewModel(factory = AppViewModelProvider.Factory)
            )
        }
        composable(route = Screens.SoldPropertiesScreen.route) {
            SoldPropertiesScreen(
                navController = navController,
                viewModel = viewModel(factory = AppViewModelProvider.Factory)
            )
        }
        composable(route = Screens.AddPropertyScreen.route) {
            SellWithUsScreen(
                navController = navController,
                viewModel = sellWithUsViewModel,
            )
        }
        composable(route = Screens.AddPropertyImagesScreen.route) {
            AddPropertyImagesScreen(
                navController = navController,
                viewModel = sellWithUsViewModel,
            )
        }
        composable(route = Screens.PropertyDetailsScreen.route) {
            PropertyDetailsScreen(
                navController = navController, viewModel = mainScreenViewModel
            )
        }
        composable(route = Screens.InquiryScreen.route) {
            InquiryScreen(navController = navController, viewModel = mainScreenViewModel)
        }
        composable(route = Screens.MyInquiresScreen.route) {
            MyInquiresScreen(navController = navController)
        }
        composable(route = Screens.NotificationsScreen.route) {
            NotificationsScreen(navController = navController)
        }
    }
}