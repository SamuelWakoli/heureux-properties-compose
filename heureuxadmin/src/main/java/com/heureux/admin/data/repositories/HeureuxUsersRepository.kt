package com.heureux.admin.data.repositories

import com.heureux.admin.data.sources.UsersDataSource
import com.heureux.admin.data.types.FeedbackItem
import com.heureux.admin.data.types.HeureuxUser
import kotlinx.coroutines.flow.Flow

/**
 * A repository that provides access to Heureux user data.
 *
 * @param dataSource The data source to use for accessing user data.
 */
class HeureuxUsersRepository(private val dataSource: UsersDataSource) : UsersRepository {
    /**
     * Gets all Heureux users.
     *
     * @param onFailure A callback to be invoked if an exception occurs.
     * @return A flow of lists of Heureux users.
     */
    override fun getAllUsers(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxUser>> =
        dataSource.getAllUsers(onFailure)

    /**
     * Gets all feedback items.
     *
     * @param onFailure A callback to be invoked if an exception occurs.
     * @return A flow of lists of feedback items.
     */
    override fun getFeedback(onFailure: (exception: Exception) -> Unit): Flow<List<FeedbackItem>> =
        dataSource.getFeedback(onFailure)

    /**
     * Updates a feedback item.
     *
     * @param feedbackItem The feedback item to update.
     * @param onSuccess A callback to be invoked if the update is successful.
     * @param onFailure A callback to be invoked if an exception occurs.
     */
    override suspend fun updateFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) = dataSource.updateFeedback(feedbackItem, onSuccess, onFailure)

    /**
     * Deletes a feedback item.
     *
     * @param feedbackItem The feedback item to delete.
     * @param onSuccess A callback to be invoked if the deletion is successful.
     * @param onFailure A callback to be invoked if an exception occurs.
     */
    override suspend fun deleteFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) = dataSource.deleteFeedback(feedbackItem, onSuccess, onFailure)
}