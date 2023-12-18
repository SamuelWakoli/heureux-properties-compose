package com.heureux.properties.data

import android.content.Context

interface AuthRepository {
//    val auth: Firebase.Auth
    suspend fun registerWithEmailPassword(
        email: String,
        password: String,
    )

    suspend fun loginWithEmailPassword(
        email: String,
        password: String,
    )

    suspend fun getUser()

    suspend fun deleteUserAccount(
        email: String
    )
}

class UserAuthRepository ( context: Context) : AuthRepository {
    //    val auth: Firebase.Auth

    override suspend fun registerWithEmailPassword(email: String, password: String) {

    }

    override suspend fun loginWithEmailPassword(email: String, password: String) {

    }

    override suspend fun getUser() {

    }

    override suspend fun deleteUserAccount(email: String) {

    }
}