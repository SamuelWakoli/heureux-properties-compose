package com.heureux.properties.data.repositories

import com.google.firebase.auth.FirebaseUser
import com.heureux.properties.data.types.UserProfileData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    )

    suspend fun signIn(
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    )

    fun getUserProfileData(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?>

    suspend fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    suspend fun updateUserProfile(
        userProfileDate: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    suspend fun createUserFirestoreData(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    suspend fun signOut()

    suspend fun deleteUserAndData(
        userProfileDate: UserProfileData,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    )
}