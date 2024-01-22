package com.heureux.admin.data.types

/**
 * Represents a property listing on the Heureux platform.
 *
 * @property id The unique identifier of the property.
 * @property name The name of the property.
 * @property price The price of the property.
 * @property location The location of the property.
 * @property sellerId The ID of the seller who listed the property.
 * @property description A description of the property.
 * @property imageUrls A list of URLs to images of the property.
 * @property purchasedBy The ID of the user who purchased the property, if any.
 */
data class HeureuxProperty(
    val id: String,
    val name: String,
    val price: String,
    val location: String,
    val sellerId: String = "Heureux Properties", // used to determine user listings
    val description: String,
    val imageUrls: List<String>,
    val purchasedBy: String? = null, // used to determine if listing is sold
)
