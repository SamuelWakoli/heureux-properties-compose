package com.heureux.properties.data.types

data class HeureuxProperty(
    val id: String,
    val name: String,
    val price: String,
    val location: String,
    val sellerId: String, // used to determine user listings
    val description: String,
    val imageUrls: List<String>,
    val purchasedBy: String? = null, // used to determine if listing is sold
)
