package com.heureux.admin.data.types

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
