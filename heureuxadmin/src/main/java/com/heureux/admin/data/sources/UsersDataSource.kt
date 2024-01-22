package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.heureux.admin.data.types.FeedbackItem
import com.heureux.admin.data.types.HeureuxUser
import kotlinx.coroutines.flow.Flow

/**
 * An interface that provides access to user and feedback data.
 */
interface UsersDataSource {
    /** The Firestore instance used to access user data. */
    val firestore: FirebaseFirestore

    /** The FirebaseStorage instance used to access user data. */
    val storage: FirebaseStorage

    /**
     * Gets a list of all users.
     *
     * @param onFailure A callback that will be invoked if an error occurs.
     * @return A Flow that emits a list of HeureuxUser objects.
     */
    fun getAllUsers(
        onFailure: (exception: Exception) -> Unit
    ): Flow<List<HeureuxUser>>

    /**
     * Gets a list of feedback items.
     *
     * @param onFailure A callback that will be invoked if an error occurs.
     * @return A Flow that emits a list of FeedbackItem objects.
     */
    fun getFeedback(
        onFailure: (exception: Exception) -> Unit
    ): Flow<List<FeedbackItem>>

    /**
     * Updates a feedback item.
     *
     * @param feedbackItem The feedback item to update.
     * @param onSuccess A callback that will be invoked if the update is successful.
     * @param onFailure A callback that will be invoked if an error occurs.
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
     * @param onSuccess A callback that will be invoked if the deletion is successful.
     * @param onFailure A callback that will be invoked if an error occurs.
     */
    suspend fun deleteFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    )
}