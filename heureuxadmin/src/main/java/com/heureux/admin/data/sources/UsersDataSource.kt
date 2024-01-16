package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.heureux.admin.data.types.FeedbackItem
import com.heureux.admin.data.types.HeureuxUser
import kotlinx.coroutines.flow.Flow

interface UsersDataSource {
    val firestore: FirebaseFirestore
    val storage: FirebaseStorage
    fun getAllUsers(
        onFailure: (exception: Exception) -> Unit
    ) : Flow<List<HeureuxUser>>

    fun getFeedback(
        onFailure: (exception: Exception) -> Unit
    ) : Flow<List<FeedbackItem>>
}