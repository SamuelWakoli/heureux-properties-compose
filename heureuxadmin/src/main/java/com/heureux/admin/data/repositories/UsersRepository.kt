package com.heureux.admin.data.repositories

import com.heureux.admin.data.types.FeedbackItem
import com.heureux.admin.data.types.HeureuxUser
import kotlinx.coroutines.flow.Flow

/**
 * An interface for interacting with a user repository.
 */
interface UsersRepository {

    /**
     * Gets all users.
     *
     * @param onFailure A callback to be invoked if an exception occurs.
     * @return A Flow of a list of HeureuxUser objects.
     */
    fun getAllUsers(
        onFailure: (exception: Exception) -> Unit
    ) : Flow<List<HeureuxUser>>

    /**
     * Gets feedback.
     *
     * @param onFailure A callback to be invoked if an exception occurs.
     * @return A Flow of a list of FeedbackItem objects.
     */
    fun getFeedback(
        onFailure: (exception: Exception) -> Unit
    ): Flow<List<FeedbackItem>>

    /**
     * Updates a feedback item.
     *
     * @param feedbackItem The feedback item to update.
     * @param onSuccess A callback to be invoked if the update is successful.
     * @param onFailure A callback to be invoked if an exception occurs.
     */
    suspend fun updateFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    /**
     * Deletes a feedback item.
     *
     * @param feedbackItem The feedback item to delete.
     * @param onSuccess A callback to be invoked if the deletion is successful.
     * @param onFailure A callback to be invoked if an exception occurs.
     */
    suspend fun deleteFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    )
}