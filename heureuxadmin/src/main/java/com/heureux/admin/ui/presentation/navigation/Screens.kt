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
    data object AddPropertyScreen : Screens(route = "add_property_screen")
    data object AddPropertyImagesScreen : Screens(route = "add_property_images_screen")
    data object UpdatePaymentScreen : Screens(route = "update_payment_screen")
    data object ArchivedPropertyInquiryScreen : Screens(route = "archived_property_inq_screen")
    data object ArchivedSellWithUsReqScreen : Screens(route = "archived_sell_with_us_screen")
    data object PropertyInquiryScreen : Screens(route = "property_inq_screen")
    data object SellWithUsRequestsScreen : Screens(route = "sell_with_us_screen")
    data object SellWithUsImagesScreen : Screens(route = "sell_with_us_images_screen")
    data object PaymentHistoryScreen : Screens(route = "payments_screen")


}