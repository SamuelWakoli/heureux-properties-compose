package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.heureux.admin.data.types.FeedbackItem
import kotlinx.coroutines.flow.Flow

/**
 * An interface for accessing user feedback data.
 */
interface UserFeedbackDataSource {
    val firestore: FirebaseFirestore

    /**
     * Gets all feedback items.
     *
     * @param onError A callback to be invoked if an error occurs.
     * @return A flow of lists of FeedbackItem objects.
     */
    fun getAllFeedbacks(
        onError: (Exception) -> Unit,
    ): Flow<List<FeedbackItem>>

    /**
     * Updates a feedback item.
     *
     * @param feedbackItem The feedback item to update.
     * @param onSuccess A callback to be invoked if the update is successful.
     * @param onError A callback to be invoked if an error occurs.
     */
    suspend fun updateFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    )

    /**
     * Deletes a feedback item.
     *
     * @param feedbackItem The feedback item to delete.
     * @param onSuccess A callback to be invoked if the deletion is successful.
     * @param onError A callback to be invoked if an error occurs.
     */
    suspend fun deleteFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    )
}