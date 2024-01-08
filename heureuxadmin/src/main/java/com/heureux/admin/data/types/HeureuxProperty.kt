package com.heureux.admin.data.types

data class HeureuxProperty(
    val id: String,
    val name: String,
    val price: String,
    val location: String,
    val seller: String, // used to determine user listings
    val description: String,
    val imageUrls: List<String>,
    val sellerCode: String, // used to determine user listings
    val purchasedBy: String? = null, // used to determine if listing is sold
)
