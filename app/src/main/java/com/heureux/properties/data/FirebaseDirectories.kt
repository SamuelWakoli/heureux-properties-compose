package com.heureux.properties.data

sealed class FirebaseDirectories (val name: String) {
    data object UsersCollection : FirebaseDirectories(name = "users")
}