package com.heureux.properties.data.sources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.heureux.properties.data.types.UserProfileData
import kotlinx.coroutines.flow.Flow

interface PropertiesDataSource {
    val auth: FirebaseAuth
    val firestore: FirebaseFirestore
    val storage: FirebaseStorage

    fun getUserProfileData(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?>


}