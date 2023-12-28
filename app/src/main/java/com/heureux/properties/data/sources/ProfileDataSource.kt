package com.heureux.properties.data.sources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.heureux.properties.data.types.UserProfileData
import kotlinx.coroutines.flow.Flow


interface ProfileDataSource {
    val auth: FirebaseAuth
    val firestore: FirebaseFirestore
    val storageReference: StorageReference

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