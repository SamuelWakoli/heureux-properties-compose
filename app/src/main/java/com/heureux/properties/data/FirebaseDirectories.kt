package com.heureux.properties.data

sealed class FirebaseDirectories (val name: String) {
    data object UsersCollection : FirebaseDirectories(name = "users")
    data object UsersStorageReference : FirebaseDirectories(name = "users")
}

sealed class FireStoreUserFields (val field: String) {
    data object PhotoUrl:FireStoreUserFields ("photoUrl")
    data object Name:FireStoreUserFields("name")
    data object Phone:FireStoreUserFields("phone")
    data object Bookmarks:FireStoreUserFields("bookmarks")
    data object PropertiesOwned:FireStoreUserFields("propertiesOwned")
    data object Listings: FireStoreUserFields("listings")
}