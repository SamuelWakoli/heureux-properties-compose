package com.heureux.admin.data.types

/**
 * Represents a Heureux user.
 *
 * @property photoUrl The URL of the user's photo.
 * @property name The user's name.
 * @property email The user's email address.
 * @property phone The user's phone number.
 */
data class HeureuxUser(
    val photoUrl: String?,
    val name: String?,
    val email: String?,
    val phone: String?,
)
