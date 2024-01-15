package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.heureux.admin.data.FirebaseDirectories
import com.heureux.admin.data.types.FeedbackItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HeureuxUserFeedbackDataSource : UserFeedbackDataSource {
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    override fun getAllFeedbacks(onError: (Exception) -> Unit): Flow<List<FeedbackItem>> =
        callbackFlow {
            val snapshotListener =
                firestore.collection(FirebaseDirectories.FeedbacksCollection.name)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            onError(error)
                            close(error)
                        } else {
                            value?.let {
                                val feedbacks = it.toObjects(FeedbackItem::class.java)
                                trySend(feedbacks)
                            }
                        }
                    }

            awaitClose { snapshotListener.remove() }
        }

    override suspend fun updateFeedback(
        feedbackItem: FeedbackItem, onSuccess: () -> Unit, onError: (Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.FeedbacksCollection.name).document(feedbackItem.id)
            .set(feedbackItem).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onError(it)
            }
    }

    override suspend fun deleteFeedback(
        feedbackItem: FeedbackItem, onSuccess: () -> Unit, onError: (Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.FeedbacksCollection.name).document(feedbackItem.id)
            .delete().addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onError(it)
            }
    }
}