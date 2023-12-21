package com.heureux.properties.ui.presentation.navigation

sealed class Screens(val route: String) {
    data object SignInScreen : Screens(route = "sign_in_screen")
    data object RegistrationScreen : Screens(route = "registration_screen")
    data object MainScreen : Screens(route = "main_screen")


    // Bottom Nav
    data object HomeScreen : Screens(route = "home")
    data object BookmarksScreen : Screens(route = "bookmarks")
    data object MyListingsScreen : Screens(route = "my_listings")
    data object MoreScreen : Screens(route = "more")

    // Other Screens
    data object ProfileScreen : Screens(route = "profile_screen")
    data object AboutUsScreen : Screens(route = "about_us_screen")
    data object ContactUsScreen : Screens(route = "contact_us_screen")
    data object FilterScreen : Screens(route = "filter_screen")
    data object FilterResultsScreen : Screens(route = "filter_results_screen")
    data object FeedbackScreen : Screens(route = "feedback_screen")
}