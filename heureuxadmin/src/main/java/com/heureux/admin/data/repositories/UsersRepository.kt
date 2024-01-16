package com.heureux.admin.data.repositories

import com.heureux.admin.data.types.FeedbackItem
import com.heureux.admin.data.types.HeureuxUser
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getAllUsers(
        onFailure: (exception: Exception) -> Unit
    ) : Flow<List<HeureuxUser>>

    fun getFeedback(
        onFailure: (exception: Exception) -> Unit
    ): Flow<List<FeedbackItem>>

    suspend fun updateFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    suspend fun deleteFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    )
}