package com.heureux.admin.data.repositories

import com.heureux.admin.data.sources.UsersDataSource
import com.heureux.admin.data.types.FeedbackItem
import com.heureux.admin.data.types.HeureuxUser
import kotlinx.coroutines.flow.Flow

class HeureuxUsersRepository(private val dataSource: UsersDataSource) : UsersRepository {
    override fun getAllUsers(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxUser>> =
        dataSource.getAllUsers(onFailure)

    override fun getFeedback(onFailure: (exception: Exception) -> Unit): Flow<List<FeedbackItem>> =
        dataSource.getFeedback(onFailure)

    override suspend fun updateFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) = dataSource.updateFeedback(feedbackItem, onSuccess, onFailure)

    override suspend fun deleteFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) = dataSource.deleteFeedback(feedbackItem, onSuccess, onFailure)
}