package com.heureux.properties.data.types

import android.net.Uri


data class UserProfileData(
    val userID: String,
    val displayName: String?,
    val photoURL: Uri?,
    val userEmail: String?,
    val phone: String? = null,
)
