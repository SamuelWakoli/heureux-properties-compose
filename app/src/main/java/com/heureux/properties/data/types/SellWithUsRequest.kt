package com.heureux.properties.data.types

data class SellWithUsRequest(
    val time: String,
    val userId: String,
    val propertyName: String,
    val propertyDescription: String,
    val propertyPrice: String,
    val propertyImages: List<String>,
    val contactNumber: String,
)
