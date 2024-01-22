package com.heureux.admin.data.repositories

import com.heureux.admin.data.sources.UserFeedbackDataSource
import com.heureux.admin.data.types.FeedbackItem
import kotlinx.coroutines.flow.Flow

/**
 * A repository that provides access to user feedback data.
 *
 * @param dataSource The data source that provides the user feedback data.
 */
class HeureuxUserFeedbackRepository(private val dataSource: UserFeedbackDataSource) :
    UserFeedbackRepository {
    /**
     * Gets all user feedback items.
     *
     * @param onError A callback that will be invoked if an error occurs.
     * @return A flow of lists of feedback items.
     */
    override fun getAllFeedbacks(onError: (Exception) -> Unit): Flow<List<FeedbackItem>> =
        dataSource.getAllFeedbacks(onError)

    /**
     * Updates a user feedback item.
     *
     * @param feedbackItem The feedback item to update.
     * @param onSuccess A callback that will be invoked if the update is successful.
     * @param onError A callback that will be invoked if an error occurs.
     */
    override suspend fun updateFeedback(
        feedbackItem: FeedbackItem, onSuccess: () -> Unit, onError: (Exception) -> Unit
    ) = dataSource.updateFeedback(feedbackItem, onSuccess, onError)

    /**
     * Deletes a user feedback item.
     *
     * @param feedbackItem The feedback item to delete.
     * @param onSuccess A callback that will be invoked if the deletion is successful.
     * @param onError A callback that will be invoked if an error occurs.
     */
    override suspend fun deleteFeedback(
        feedbackItem: FeedbackItem, onSuccess: () -> Unit, onError: (Exception) -> Unit
    ) = dataSource.deleteFeedback(feedbackItem, onSuccess, onError)
}