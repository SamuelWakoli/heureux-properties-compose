package com.heureux.admin.ui.presentation.navigation

sealed class Screens(val route: String) {
    data object SignInScreen : Screens(route = "sign_in_screen")
    data object RegistrationScreen : Screens(route = "registration_screen")
    data object ForgotPasswordScreen: Screens(route = "forgot_password_screen")

    data object MainScreen : Screens(route = "main_screen")

    // Main screen bottom nav destinations


    // Bottom Nav
    data object HomeScreen : Screens(route = "home")
    data object UsersScreen : Screens(route = "users")
    data object InquiriesScreen : Screens(route = "my_listings")
    data object MoreScreen : Screens(route = "more")

    // Other Screens
    data object ProfileScreen : Screens(route = "profile_screen")
    data object AdministrationScreen : Screens(route = "administration_screen")
    data object PropertyDetailsScreen : Screens(route = "property_details_screen")


}