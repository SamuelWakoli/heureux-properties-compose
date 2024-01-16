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

class HeureuxUsersDataSource : UsersDataSource {
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore
    override val storage: FirebaseStorage
        get() = Firebase.storage

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

    override fun getFeedback(onFailure: (exception: Exception) -> Unit): Flow<List<FeedbackItem>> =
        callbackFlow {
            val snapshotListener =
                firestore.collection(FirebaseDirectories.FeedbacksCollection.name)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            close(error)
                            onFailure(error)
                        } else {
                            val feedbacks = mutableListOf<FeedbackItem>()
                            for (document in value!!.documents) {
                                val feedback = FeedbackItem(
                                    id = document.id,
                                    message = document.get("message").toString(),
                                    time = document.get("time").toString(),
                                    senderEmail = document.get("senderEmail").toString(),
                                    isRead = document.getBoolean("isRead") as Boolean
                                )
                                feedbacks.add(feedback)
                            }
                            trySend(feedbacks)
                        }

                    }

            awaitClose { snapshotListener.remove() }
        }


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