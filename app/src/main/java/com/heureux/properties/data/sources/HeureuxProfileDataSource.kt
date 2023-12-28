package com.heureux.properties.data.sources

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.heureux.properties.data.FireStoreUserFields
import com.heureux.properties.data.FirebaseDirectories
import com.heureux.properties.data.types.UserProfileData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class HeureuxProfileDataSource : ProfileDataSource {
    override val auth: FirebaseAuth = Firebase.auth
    override val firestore: FirebaseFirestore = Firebase.firestore
    override val storageReference: StorageReference = Firebase.storage.reference

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override suspend fun createUserFirestoreData(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        val data = hashMapOf(
            FireStoreUserFields.PhotoUrl.field to user.photoUrl,
            FireStoreUserFields.Name.field to user.displayName,
            FireStoreUserFields.Phone.field to null,
            FireStoreUserFields.Bookmarks.field to emptyList<String>(),
            FireStoreUserFields.PropertiesOwned.field to emptyList<String>(),
            FireStoreUserFields.Listings.field to emptyList<String>(),
        )

        firestore.collection(FirebaseDirectories.UsersCollection.name).document(user.email!!)
            .set(data).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    override suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authResultTask ->
                // since user registration is successful, update display name
                Firebase.auth.currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                )

                val user = authResultTask.result.user

                coroutineScope.launch {
                    createUserFirestoreData(
                        user = user!!,
                        onSuccess = {
                            onSuccessListener()
                        },
                        onFailure = { exception ->
                            // invoke this
                            onSuccessListener()
                            // because we don't want to prevent the user from signing in.
                            // When this firestore task fails, we will rerun it again when
                            // trying to access the non-existing.
                        }
                    )
                }
            }.addOnFailureListener { exception ->
                onErrorListener(exception)
            }
    }

    override suspend fun signIn(
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { authResultTask ->
                onSuccessListener()

            }.addOnFailureListener { exception ->
                onErrorListener(exception)
            }
    }

    override fun getUserProfileData(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?> = callbackFlow {

        val snapshotListener = firestore.collection(FirebaseDirectories.UsersCollection.name)
            .document(user.email!!)
            .addSnapshotListener { value, error ->

                if (error != null) {
                    onFailure(error)
                    close(error) // Close the flow with an error
                } else {
                    if (value?.data.isNullOrEmpty()) { // Document does not exist
                        coroutineScope.launch {
                            createUserFirestoreData(
                                user = user,
                                onSuccess = {
                                },
                                onFailure = { exception ->
                                }
                            )
                        }

                    } else {
                        trySend(
                            UserProfileData(
                                userID = value?.id!!,
                                displayName = value.getString(FireStoreUserFields.Name.field),
                                photoURL = Uri.parse(value.getString(FireStoreUserFields.PhotoUrl.field)),
                                userEmail = value.getString("email"),
                                phone = value.getString(FireStoreUserFields.Phone.field),
                            )
                        ).isSuccess // Offer the latest DocumentSnapshot
                    }
                    onSuccess()

                }
            }

        awaitClose { snapshotListener.remove() }
    }

    override suspend fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    override suspend fun updateUserProfile(
        userProfileDate: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        val userdata = hashMapOf(
            FireStoreUserFields.PhotoUrl.field to userProfileDate.photoURL,
            FireStoreUserFields.Name.field to userProfileDate.displayName,
            FireStoreUserFields.Phone.field to userProfileDate.phone,
        )

        firestore.collection(FirebaseDirectories.UsersCollection.name)
            .document(userProfileDate.userEmail!!)
            .update(userdata as Map<String, Any>)

        auth.currentUser?.updateProfile(
            UserProfileChangeRequest.Builder()
                .setDisplayName(userProfileDate.displayName)
                .setPhotoUri(userProfileDate.photoURL)
                .build()
        )
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun deleteUserAndData(
        userProfileDate: UserProfileData,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    ) {
        firestore.collection(FirebaseDirectories.UsersCollection.name)
            .document(userProfileDate.userEmail!!).delete().addOnSuccessListener {

                storageReference.child(FirebaseDirectories.UsersStorageReference.name)
                    .child(userProfileDate.userEmail).delete()
                auth.currentUser?.delete()

                onSuccessListener()
            }.addOnFailureListener { exception ->
                onErrorListener(exception)
            }
    }
}