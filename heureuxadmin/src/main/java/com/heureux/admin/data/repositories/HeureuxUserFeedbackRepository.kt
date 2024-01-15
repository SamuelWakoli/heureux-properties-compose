package com.heureux.admin.data.repositories

import com.heureux.admin.data.sources.UserFeedbackDataSource
import com.heureux.admin.data.types.FeedbackItem
import kotlinx.coroutines.flow.Flow

class HeureuxUserFeedbackRepository(private val dataSource: UserFeedbackDataSource) :
    UserFeedbackRepository {
    override fun getAllFeedbacks(onError: (Exception) -> Unit): Flow<List<FeedbackItem>> =
        dataSource.getAllFeedbacks(onError)

    override suspend fun updateFeedback(
        feedbackItem: FeedbackItem, onSuccess: () -> Unit, onError: (Exception) -> Unit
    ) = dataSource.updateFeedback(feedbackItem, onSuccess, onError)

    override suspend fun deleteFeedback(
        feedbackItem: FeedbackItem, onSuccess: () -> Unit, onError: (Exception) -> Unit
    ) = dataSource.deleteFeedback(feedbackItem, onSuccess, onError)
}