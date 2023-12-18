package com.heureux.properties.data

data class HeureuxUser(
    val name: String,
    val email: String,
    val phone: String,

    // a list of ids
    val bookmarks: List<String>,
    val propertiesOwned: List<String>,
    val listings: List<String>,
)
