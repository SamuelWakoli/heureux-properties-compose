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
import com.heureux.admin.ui.presentation.screens.edit_profile_screen.EditProfileScreen
import com.heureux.admin.ui.presentation.screens.edit_profile_screen.EditProfileScreenViewModel
import com.heureux.admin.ui.presentation.screens.inquiries_screens.InquiriesViewModel
import com.heureux.admin.ui.presentation.screens.inquiries_screens.archived_property_inquiry.ArchivedPropertyInquiryScreen
import com.heureux.admin.ui.presentation.screens.inquiries_screens.archived_sell_with_us.ArchivedSellWithUsReqScreen
import com.heureux.admin.ui.presentation.screens.inquiries_screens.property_inquiry.PropertyInquiryScreen
import com.heureux.admin.ui.presentation.screens.inquiries_screens.sell_with_us.SellWithUsImagesScreen
import com.heureux.admin.ui.presentation.screens.inquiries_screens.sell_with_us.SellWithUsRequestScreen
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreen
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreenViewModel
import com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.home_screen.HomeScreenViewModel
import com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.more_screen.MoreScreenViewModel
import com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.users_screen.UsersScreenViewModel
import com.heureux.admin.ui.presentation.screens.payment_history_screen.PaymentHistoryScreen
import com.heureux.admin.ui.presentation.screens.profile_screen.ProfileScreen
import com.heureux.admin.ui.presentation.screens.profile_screen.ProfileScreenViewModel
import com.heureux.admin.ui.presentation.screens.property_details_screen.PropertyDetailsScreen
import com.heureux.admin.ui.presentation.screens.update_payment_screen.UpdatePaymentScreen
import com.heureux.admin.ui.presentation.screens.user_feedback_screen.UserFeedbackScreen

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

    val homeScreenViewModel = viewModel<HomeScreenViewModel>(factory = AppViewModelProvider.Factory)

    val usersScreenViewModel =
        viewModel<UsersScreenViewModel>(factory = AppViewModelProvider.Factory)

    val inquiriesViewModel = viewModel<InquiriesViewModel>(factory = AppViewModelProvider.Factory)

    val moreScreenViewModel = viewModel<MoreScreenViewModel>(factory = AppViewModelProvider.Factory)

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
                homeScreenViewModel = homeScreenViewModel,
                usersScreenViewModel = usersScreenViewModel,
                moreScreenViewModel = moreScreenViewModel,
            )
        }
        composable(Screens.ProfileScreen.route) {
            ProfileScreen(
                navController = navController,
                viewModel = profileScreenViewModel
            )
        }
        composable(Screens.EditProfileScreen.route) {
            EditProfileScreen(
                navController = navController,
                viewModel = viewModel<EditProfileScreenViewModel>(factory = AppViewModelProvider.Factory)
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
                viewModel = homeScreenViewModel,
            )
        }
        composable(Screens.AddPropertyScreen.route) {
            AddPropertyScreen(
                navController = navController,
                viewModel = addPropertyScreenViewModel,
                homeScreenViewModel = homeScreenViewModel,
            )
        }
        composable(Screens.AddPropertyImagesScreen.route) {
            AddPropertyImagesScreen(
                navController = navController,
                viewModel = addPropertyScreenViewModel,
            )
        }
        composable(Screens.UpdatePaymentScreen.route) {
            UpdatePaymentScreen(
                navController = navController,
                viewModel = usersScreenViewModel,
            )
        }
        composable(Screens.ArchivedPropertyInquiryScreen.route) {
            ArchivedPropertyInquiryScreen(
                navController = navController,
                viewModel = inquiriesViewModel,
            )
        }
        composable(Screens.ArchivedSellWithUsReqScreen.route) {
            ArchivedSellWithUsReqScreen(
                navController = navController,
                viewModel = inquiriesViewModel,
            )
        }
        composable(Screens.PropertyInquiryScreen.route) {
            PropertyInquiryScreen(
                navController = navController,
                viewModel = inquiriesViewModel,
            )
        }
        composable(Screens.SellWithUsRequestsScreen.route) {
            SellWithUsRequestScreen(
                navController = navController,
                viewModel = inquiriesViewModel,
            )
        }
        composable(Screens.SellWithUsImagesScreen.route) {
            SellWithUsImagesScreen(
                navController = navController,
                viewModel = inquiriesViewModel,
            )
        }
        composable(Screens.PaymentHistoryScreen.route) {
            PaymentHistoryScreen(
                navController = navController,
                viewModel = moreScreenViewModel,
            )
        }
        composable(Screens.UserFeedbackScreen.route) {
            UserFeedbackScreen(
                navController = navController,
                viewModel = moreScreenViewModel,
            )
        }
    }
}