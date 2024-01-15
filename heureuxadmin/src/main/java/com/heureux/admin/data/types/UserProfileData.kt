package com.heureux.admin.data.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserProfileData(
    @SerialName("displayName") val displayName: String?,
    @SerialName("photoURL")   val photoURL: String?,
    @SerialName("userEmail")val userEmail: String?,
    @SerialName("phone")val phone: String? = null,
)
