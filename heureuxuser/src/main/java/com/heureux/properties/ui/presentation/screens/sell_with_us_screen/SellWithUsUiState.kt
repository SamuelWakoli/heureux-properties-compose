package com.heureux.properties.ui.presentation.screens.sell_with_us_screen

data class SellWithUsUiState(
    val propertyName: String = "",
    val propertyNameError: Boolean = false,
    val propertyDescription: String = "",
    val propertyDescriptionError: Boolean = false,
    val propertyPrice: String = "",
    val propertyPriceError: Boolean = false,
    val propertiesImagesCount: Int = 0,
    val propertyImages: MutableList<String> = mutableListOf(),
    val propertyImagesError: Boolean= false,
    val userPhoneNumber: String = "",
    val userPhoneNumberError: Boolean = false,
    val uploadingRequest: Boolean = false,
)
