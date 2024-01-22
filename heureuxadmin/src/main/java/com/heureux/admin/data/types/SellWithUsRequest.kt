package com.heureux.admin.data.types

/**
 * Represents a request to sell a property with us.
 *
 * @property id The unique identifier of the request.
 * @property time The time when the request was made.
 * @property userId The ID of the user who made the request.
 * @property propertyName The name of the property.
 * @property propertyDescription A description of the property.
 * @property propertyPrice The price of the property.
 * @property propertyImages A list of images of the property.
 * @property contactNumber The contact number of the user.
 * @property archived Whether the request is archived or not.
 */
data class SellWithUsRequest(
    val id: String,
    val time: String,
    val userId: String,
    val propertyName: String,
    val propertyDescription: String,
    val propertyPrice: String,
    val propertyImages: List<String>,
    val contactNumber: String,
    val archived: Boolean = false,
)
