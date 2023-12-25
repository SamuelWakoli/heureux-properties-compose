package com.heureux.properties.data

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

interface FirestoreRepository {
    suspend fun createHeureuxUser(
        userData: HeureuxUser,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    fun getHeureuxUserData(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<HeureuxUser?>
}


class HeureuxFirestoreRepository(context: Context) : FirestoreRepository {
    private val firestore: FirebaseFirestore = Firebase.firestore
    private val usersCollection = "users"

    companion object {
        private var TAG = HeureuxFirestoreRepository::class.java.simpleName
    }

    private val coroutineScope = CoroutineScope(context = Dispatchers.IO)

    override suspend fun createHeureuxUser(
        userData: HeureuxUser,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        val data = hashMapOf(
            "photoUrl" to userData.photoUrl,
            "name" to userData.name,
            "email" to userData.email,
            "phone" to userData.phone,
            "bookmarks" to userData.bookmarks,
            "propertiesOwned" to userData.propertiesOwned,
            "listings" to userData.listings,
        )
        firestore.collection(usersCollection).document(userData.email!!).set(data)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    override fun getHeureuxUserData(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<HeureuxUser?> = callbackFlow {

        val snapshotListener = firestore.collection(usersCollection)
            .document(user.email!!)
            .addSnapshotListener { value, error ->

                if (error != null) {
                    onFailure(error)
                    // Handle error here, e.g., log it or notify the user
                    Log.e(TAG, "Error fetching user data", error)
                    close(error) // Close the flow with an error

                } else {

                    if (value?.data.isNullOrEmpty()) { // Document does not exist
                        coroutineScope.launch {
                            createHeureuxUser(
                                HeureuxUser(
                                    photoUrl = user.photoUrl.toString(),
                                    name = user.displayName!!,
                                    email = user.email!!,
                                    phone = user.phoneNumber,
                                    bookmarks = emptyList(),
                                    propertiesOwned = emptyList(),
                                    listings = emptyList()
                                ),
                                onSuccess = {},
                                onFailure = {
                                    Log.e(TAG, "Error creating user", it)
                                }
                            )
                        }

                    } else {
                        trySend(
                            HeureuxUser(
                                photoUrl = value?.getString("photoUrl"),
                                name = value?.getString("name")!!,
                                email = value.getString("email")!!,
                                phone = value.getString("phone"),
                                bookmarks = value.get("bookmarks") as? List<String>
                                    ?: emptyList(),
                                propertiesOwned = value.get("propertiesOwned") as? List<String>
                                    ?: emptyList(),
                                listings = value.get("listings") as? List<String>
                                    ?: emptyList()
                            )
                        ).isSuccess // Offer the latest DocumentSnapshot
                    }
                    onSuccess()

                }
            }

        awaitClose { snapshotListener.remove() }
    }

}