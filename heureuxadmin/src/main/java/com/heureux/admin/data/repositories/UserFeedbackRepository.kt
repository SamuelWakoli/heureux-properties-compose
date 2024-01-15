package com.heureux.admin.data.repositories

import com.heureux.admin.data.types.FeedbackItem
import kotlinx.coroutines.flow.Flow

interface UserFeedbackRepository {

    fun getAllFeedbacks(
        onError: (Exception) -> Unit,
    ): Flow<List<FeedbackItem>>

    suspend fun updateFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    )

    suspend fun deleteFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
    )
}