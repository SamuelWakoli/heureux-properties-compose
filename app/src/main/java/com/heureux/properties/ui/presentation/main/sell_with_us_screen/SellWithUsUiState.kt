package com.heureux.properties.ui.presentation.main.sell_with_us_screen

data class SellWithUsUiState(
    val propertyName: String = "",
    val propertyDescription: String = "",
    val propertyPrice: String = "",
    val propertyImages: List<String> = emptyList(),
    val userPhoneNumber: String = "",
)
