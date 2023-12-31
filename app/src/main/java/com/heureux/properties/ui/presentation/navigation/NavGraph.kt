package com.heureux.properties.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.heureux.properties.ui.presentation.authgate.ForgotPasswordScreen
import com.heureux.properties.ui.presentation.authgate.RegistrationScreen
import com.heureux.properties.ui.presentation.authgate.SignInScreen
import com.heureux.properties.ui.presentation.main.about_us_screen.AboutUsScreen
import com.heureux.properties.ui.presentation.main.contact_us_screen.ContactUsScreen
import com.heureux.properties.ui.presentation.main.edit_profile_screen.EditProfileScreen
import com.heureux.properties.ui.presentation.main.feedback_screen.FeedbackScreen
import com.heureux.properties.ui.presentation.main.filter_results_screen.FilterResultsScreen
import com.heureux.properties.ui.presentation.main.filter_screen.FilterScreen
import com.heureux.properties.ui.presentation.main.main_screen.MainScreen
import com.heureux.properties.ui.presentation.main.my_inquires_screen.MyInquiresScreen
import com.heureux.properties.ui.presentation.main.my_properties_screen.MyPropertiesScreen
import com.heureux.properties.ui.presentation.main.notifications_screen.NotificationsScreen
import com.heureux.properties.ui.presentation.main.payment_history_screen.PaymentHistoryScreen
import com.heureux.properties.ui.presentation.main.profile_screen.ProfileScreen
import com.heureux.properties.ui.presentation.main.property_action_screens.AddPropertyScreen
import com.heureux.properties.ui.presentation.main.property_action_screens.EditPropertyScreen
import com.heureux.properties.ui.presentation.main.property_action_screens.InquiryScreen
import com.heureux.properties.ui.presentation.main.property_action_screens.PropertyDetailsScreen
import com.heureux.properties.ui.presentation.main.sold_properties_screen.SoldPropertiesScreen
import com.heureux.properties.ui.presentation.viewmodels.AppViewModelProvider
import com.heureux.properties.ui.presentation.viewmodels.AuthViewModel
import com.heureux.properties.ui.presentation.viewmodels.MainScreenViewModel

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
        startDestination = if (currentUserSignedIn) Screens.MainScreen.route else Screens.SignInScreen.route
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(
                navController = navController,
                viewModel = authViewModel,
                onSignInWithGoogle = { onSignInWithGoogle() }
            )
        }
        composable(route = Screens.RegistrationScreen.route) {
            RegistrationScreen(
                navController = navController,
                viewModel = authViewModel,
                onSignInWithGoogle = { onSignInWithGoogle() }
            )
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
                editProfileScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
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
            FeedbackScreen(navController = navController)
        }
        composable(route = Screens.MyPropertiesScreen.route) {
            MyPropertiesScreen(navController = navController)
        }
        composable(route = Screens.PaymentHistoryScreen.route) {
            PaymentHistoryScreen(navController = navController)
        }
        composable(route = Screens.SoldPropertiesScreen.route) {
            SoldPropertiesScreen(navController = navController)
        }
        composable(route = Screens.AddPropertyScreen.route) {
            AddPropertyScreen()
        }
        composable(route = Screens.EditPropertyScreen.route) {
            EditPropertyScreen()
        }
        composable(route = Screens.PropertyDetailsScreen.route) {
            PropertyDetailsScreen(
                navController = navController
            )
        }
        composable(route = Screens.InquiryScreen.route) {
            InquiryScreen()
        }
        composable(route = Screens.MyInquiresScreen.route) {
            MyInquiresScreen()
        }
        composable(route = Screens.NotificationsScreen.route) {
            NotificationsScreen()
        }
    }
}