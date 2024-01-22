package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.heureux.admin.data.FirebaseDirectories
import com.heureux.admin.data.types.FeedbackItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * A data source that provides user feedback data from a Firestore database.
 */
class HeureuxUserFeedbackDataSource : UserFeedbackDataSource {
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    /**
     * Gets all feedback items from the Firestore database.
     *
     * @param onError A callback function that is invoked if an error occurs.
     * @return A Flow that emits a list of FeedbackItem objects.
     */
    override fun getAllFeedbacks(onError: (Exception) -> Unit): Flow<List<FeedbackItem>> =
        callbackFlow {
            val snapshotListener =
                firestore.collection(FirebaseDirectories.FeedbacksCollection.name)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            onError(error)
                            close(error)
                        } else {
                            if (value != null) {
                                val feedbacks: MutableList<FeedbackItem> = mutableListOf()
                                value.documents.forEach { document ->
                                    feedbacks.add(
                                        FeedbackItem(
                                            id = document.id,
                                            message = document.data?.get("message").toString(),
                                            time = document.data?.get("time").toString(),
                                            senderEmail = document.data?.get("senderEmail")
                                                .toString(),
                                            read = document.data?.get("isRead") as Boolean
                                        )
                                    )
                                }
                                trySend(feedbacks)
                            }
                        }
                    }

            awaitClose { snapshotListener.remove() }
        }

    /**
     * Updates a feedback item in the Firestore database.
     *
     * @param feedbackItem The feedback item to update.
     * @param onSuccess A callback function that is invoked if the update is successful.
     * @param onError A callback function that is invoked if an error occurs.
     */
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

    /**
     * Deletes a feedback item from the Firestore database.
     *
     * @param feedbackItem The feedback item to delete.
     * @param onSuccess A callback function that is invoked if the deletion is successful.
     * @param onError A callback function that is invoked if an error occurs.
     */
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