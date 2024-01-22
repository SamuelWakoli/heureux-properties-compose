package com.heureux.properties.data.sources

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.heureux.properties.data.FireStoreUserFields
import com.heureux.properties.data.FirebaseDirectories
import com.heureux.properties.data.types.UserProfileData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * A data source for Heureux profiles.
 */
class HeureuxProfileDataSource : ProfileDataSource {
    override val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override val storageReference: StorageReference = Firebase.storage.reference

    private val coroutineScope = CoroutineScope(Dispatchers.Default)


    /**
     * Uploads an image to Firebase Storage and returns the download URL.
     *
     * @param uri The URI of the image to upload.
     * @param directory The directory in Firebase Storage to upload the image to.
     * @param onSuccessListener A callback function that will be invoked with the download URL of the uploaded image.
     * @param onFailure A callback function that will be invoked if the upload fails.
     */
    override suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        val storage = Firebase.storage
        val storageReference = storage.reference.child(directory)
        val uploadTask = storageReference.putFile(uri)

        // Wait for the upload to complete, then get the download URL
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw task.exception!!
            }
            storageReference.downloadUrl
        }.addOnSuccessListener {
            onSuccessListener.invoke(it.toString())
        }.addOnFailureListener {
            onFailure.invoke(it)
        }
    }


    /**
     * Gets the current user.
     *
     * @return A Flow that emits the current user.
     */
    override suspend fun getCurrentUser(): Flow<FirebaseUser?> = callbackFlow {
        val snapshotListener = auth.addAuthStateListener {
            trySend(it.currentUser)
        }
        awaitClose {}
    }

    /**
     * Creates a user in Firestore.
     *
     * @param user The user data.
     * @param onSuccess The callback to be executed on success.
     * @param onFailure The callback to be executed on failure.
     */
    override suspend fun createUserFirestoreData(
        user: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        val data = hashMapOf(
            FireStoreUserFields.PhotoUrl.field to user.photoURL,
            FireStoreUserFields.Name.field to user.displayName,
            FireStoreUserFields.Phone.field to null,
        )

        try {
            firestore.collection(FirebaseDirectories.UsersCollection.name)
                .document(user.userEmail!!).set(data).await()
            onSuccess()
        } catch (exception: Exception) {
            onFailure(exception)
        }
    }

    /**
     * Registers a new user.
     *
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password.
     * @param onSuccessListener The callback to be executed on success.
     * @param onErrorListener The callback to be executed on failure.
     */
    override suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    ) {
        try {
            val authResultTask = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResultTask.user!!
            coroutineScope.launch {
                user.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(name).build()
                ).await()

                try {
                    createUserFirestoreData(user = UserProfileData(
                        displayName = name,
                        photoURL = user.photoUrl.toString(),
                        userEmail = email,
                        phone = user.phoneNumber,
                    ), onSuccess = {}, onFailure = {})
                    onSuccessListener()
                } catch (exception: Exception) {
                    onErrorListener(exception)
                }
            }
        } catch (exception: Exception) {
            onErrorListener(exception)
        }
    }

    /**
     * Signs in a user.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @param onSuccessListener The callback to be executed on success.
     * @param onErrorListener The callback to be executed on failure.
     */
    override suspend fun signIn(
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    ) {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            onSuccessListener()
        } catch (exception: Exception) {
            onErrorListener(exception)
        }
    }

    /**
     * Gets the user profile data from Firestore.
     *
     * @param onSuccess A callback to be invoked when the data is successfully retrieved.
     * @param onFailure A callback to be invoked if an error occurs.
     * @return A Flow that emits the UserProfileData object.
     */
    override fun getUserProfileData(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?> = callbackFlow {

        val snapshotListener = firestore.collection(FirebaseDirectories.UsersCollection.name)
            .document(auth.currentUser?.email!!).addSnapshotListener { value, error ->

                if (error != null) {
                    onFailure(error)
                    close(error) // Close the flow with an error
                } else {
                    if (value?.data.isNullOrEmpty()) { // Document does not exist
                        coroutineScope.launch {
                            val user = auth.currentUser!!

                            createUserFirestoreData(user = UserProfileData(
                                displayName = user.displayName,
                                photoURL = user.photoUrl.toString(),
                                userEmail = user.email,
                                phone = user.phoneNumber,
                            ), onSuccess = {}, onFailure = {})
                        }
                    } else {
                        trySend(
                            UserProfileData(
                                userEmail = value!!.id,
                                displayName = value.getString(FireStoreUserFields.Name.field),
                                photoURL = value.getString(FireStoreUserFields.PhotoUrl.field)
                                    ?: "",
                                phone = value.getString(FireStoreUserFields.Phone.field),
                            )
                        ).isSuccess // Offer the latest DocumentSnapshot
                    }
                    onSuccess()
                }
            }

        awaitClose { snapshotListener.remove() }
    }

    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email The email address to send the password reset email to.
     * @param onSuccess A callback to be invoked when the email is successfully sent.
     * @param onFailure A callback to be invoked if an error occurs.
     */
    override suspend fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        try {
            auth.sendPasswordResetEmail(email).await()
            onSuccess()
        } catch (exception: Exception) {
            onFailure(exception)
        }
    }

    /**
     * Updates the user profile.
     *
     * @param userProfileDate The updated user profile data.
     * @param onSuccess A callback to be invoked when the profile is successfully updated.
     * @param onFailure A callback to be invoked if an error occurs.
     */
    override suspend fun updateUserProfile(
        userProfileDate: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        val userdata = hashMapOf<String, Any>(
            FireStoreUserFields.PhotoUrl.field to userProfileDate.photoURL.toString(),
            FireStoreUserFields.Name.field to userProfileDate.displayName.toString(),
            FireStoreUserFields.Phone.field to userProfileDate.phone.toString(),
        )

        try {
            firestore.collection(FirebaseDirectories.UsersCollection.name)
                .document(userProfileDate.userEmail!!).update(userdata).await()

            auth.currentUser?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(userProfileDate.displayName)
                    .setPhotoUri(Uri.parse(userProfileDate.photoURL)).build()
            )?.await()

            // Reload the user profile to ensure that changes are reflected immediately
            auth.currentUser?.reload()?.await()

            onSuccess()
        } catch (exception: Exception) {
            onFailure(exception)
        }
    }


    /**
     * Signs out the current user.
     */
    override suspend fun signOut() {
        auth.signOut()
    }

    /**
     * Deletes the user and their data.
     *
     * @param email The email address of the user to delete.
     * @param onSuccessListener A callback to be invoked when the user is successfully deleted.
     * @param onErrorListener A callback to be invoked if an error occurs.
     */
    override suspend fun deleteUserAndData(
        email: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    ) {
        try {
            firestore.collection(FirebaseDirectories.UsersCollection.name).document(email).delete()
                .await()

            storageReference.child(FirebaseDirectories.UsersStorageReference.name).child(email)
                .delete().await()
            auth.currentUser?.delete()?.await()

            onSuccessListener()
        } catch (exception: Exception) {
            onErrorListener(exception)
        }
    }
}
