package com.heureux.admin.data.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Represents user profile data.
 *
 * @property displayName The user's display name.
 * @property photoURL The user's photo URL.
 * @property userEmail The user's email address.
 * @property phone The user's phone number.
 */
@Serializable
data class UserProfileData(
    @SerialName("displayName") val displayName: String?,
    @SerialName("photoURL")   val photoURL: String?,
    @SerialName("userEmail")val userEmail: String?,
    @SerialName("phone")val phone: String? = null,
)
