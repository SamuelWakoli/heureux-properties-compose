package com.heureux.properties.data.types

data class HeureuxProperty(
    val name: String,
    val price: String,
    val location: String,
    val seller: String, // used to determine user listings
    val description: String,
    val imageUrls: List<String>,
)
