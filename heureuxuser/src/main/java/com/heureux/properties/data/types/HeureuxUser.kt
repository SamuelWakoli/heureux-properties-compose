package com.heureux.properties.data.types

/**
 * Represents a Heureux user.
 *
 * @property photoUrl The URL of the user's photo.
 * @property name The user's name.
 * @property email The user's email address.
 * @property phone The user's phone number.
 * @property bookmarks A list of IDs of the user's bookmarks.
 * @property propertiesOwned A list of IDs of the properties owned by the user.
 * @property listings A list of IDs of the user's listings.
 */
data class HeureuxUser(
    val photoUrl: String?,
    val name: String?,
    val email: String?,
    val phone: String?,

    // a list of ids
    val bookmarks: List<String>?,
    val propertiesOwned: List<String>?,
    val listings: List<String>?,
)
