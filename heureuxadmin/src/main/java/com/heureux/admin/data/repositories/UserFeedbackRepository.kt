package com.heureux.admin.data.repositories

import com.heureux.admin.data.types.FeedbackItem
import kotlinx.coroutines.flow.Flow

/**
 * A repository for user feedback.
 */
interface UserFeedbackRepository {

    /**
     * Gets all feedback items.
     *
     * @param onError A callback to be invoked if an error occurs.
     * @return A flow of lists of feedback items.
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