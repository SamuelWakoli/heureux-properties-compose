package com.heureux.admin.ui.presentation.navigation

sealed class Screens(val route: String) {
    data object SignInScreen : Screens(route = "sign_in_screen")
    data object RegistrationScreen : Screens(route = "registration_screen")
    data object ForgotPasswordScreen: Screens(route = "forgot_password_screen")

    data object MainScreen : Screens(route = "main_screen")


}