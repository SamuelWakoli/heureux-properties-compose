package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.heureux.admin.data.FirebaseDirectories
import com.heureux.admin.data.types.FeedbackItem
import com.heureux.admin.data.types.HeureuxUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * A data source for Heureux users and feedback.
 */
class HeureuxUsersDataSource : UsersDataSource {
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore
    override val storage: FirebaseStorage
        get() = Firebase.storage

    /**
     * Gets all Heureux users.
     *
     * @param onFailure A callback to be invoked if an error occurs.
     * @return A Flow of lists of HeureuxUser objects.
     */
    override fun getAllUsers(
        onFailure: (exception: Exception) -> Unit
    ): Flow<List<HeureuxUser>> = callbackFlow {
        val snapshotListener = firestore.collection(FirebaseDirectories.UsersCollection.name)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    onFailure(error)
                } else {
                    val users = mutableListOf<HeureuxUser>()
                    for (document in snapshot!!.documents) {
                        val user = HeureuxUser(
                            photoUrl = document.getString("photoUrl"),
                            name = document.getString("name"),
                            email = document.id,
                            phone = document.getString("phone"),
                        )
                        user.let { users.add(it) }
                    }
                    trySend(users)
                }
            }

        awaitClose { snapshotListener.remove() }
    }

    /**
     * Gets all feedback items.
     *
     * @param onFailure A callback to be invoked if an error occurs.
     * @return A Flow of lists of FeedbackItem objects.
     */
    override fun getFeedback(onFailure: (exception: Exception) -> Unit): Flow<List<FeedbackItem>> =
        callbackFlow {
            val snapshotListener =
                firestore.collection(FirebaseDirectories.FeedbacksCollection.name)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            close(error)
                            onFailure(error)
                        } else {
                            if (value != null) {
                                val feedbacks = mutableListOf<FeedbackItem>()
                                for (document in value.documents) {
                                    val feedback = FeedbackItem(
                                        id = document.id,
                                        message = document.get("message").toString(),
                                        time = document.get("time").toString(),
                                        senderEmail = document.get("senderEmail").toString(),
                                        read = document.get("read") as Boolean
                                    )
                                    feedbacks.add(feedback)
                                }
                                trySend(feedbacks)
                            }
                        }
                    }

            awaitClose { snapshotListener.remove() }
        }


    /**
     * Updates a feedback item.
     *
     * @param feedbackItem The feedback item to update.
     * @param onSuccess A callback to be invoked if the update is successful.
     * @param onFailure A callback to be invoked if an error occurs.
     */
    override suspend fun updateFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.FeedbacksCollection.name).document(feedbackItem.id)
            .set(feedbackItem).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFailure(it)
            }
    }

    /**
     * Deletes a feedback item.
     *
     * @param feedbackItem The feedback item to delete.
     * @param onSuccess A callback to be invoked if the deletion is successful.
     * @param onFailure A callback to be invoked if an error occurs.
     */
    override suspend fun deleteFeedback(
        feedbackItem: FeedbackItem,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.FeedbacksCollection.name).document(feedbackItem.id)
            .delete().addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFailure(it)
            }
    }
}