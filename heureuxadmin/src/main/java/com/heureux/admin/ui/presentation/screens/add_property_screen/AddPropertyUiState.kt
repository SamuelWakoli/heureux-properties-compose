package com.heureux.admin.ui.presentation.screens.add_property_screen

data class AddPropertyUiState(
    val propertyName: String = "",
    val propertyNameError: Boolean = false,
    val propertyDescription: String = "",
    val propertyDescriptionError: Boolean = false,
    val propertyPrice: String = "",
    val propertyPriceError: Boolean = false,
    val propertyLocation: String = "",
    val propertyLocationError: Boolean = false,
    val propertiesImagesCount: Int = 0,
    val propertyImages: MutableList<String> = mutableListOf(),
    val propertyImagesError: Boolean= false,
    val userPhoneNumber: String = "",
    val userPhoneNumberError: Boolean = false,
    val uploadingRequest: Boolean = false,
)
