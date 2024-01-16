package com.heureux.admin.data.sources


import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.heureux.admin.data.FirebaseDirectories
import com.heureux.admin.data.types.UserProfileData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class HeureuxProfileDataSource : ProfileDataSource {
    override val auth: FirebaseAuth by lazy { Firebase.auth }
    override val firestore: FirebaseFirestore by lazy { Firebase.firestore }
    override val storageReference: StorageReference by lazy { Firebase.storage.reference }

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val TAG = "HeureuxProfileDataSource"

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


    override suspend fun getCurrentUser(): Flow<FirebaseUser?> = callbackFlow {
        val snapshotListener = auth.addAuthStateListener {
            trySend(it.currentUser)
        }
    }

    override suspend fun createUserFirestoreData(
        user: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {

        try {
            firestore.collection(FirebaseDirectories.AdminsCollection.name)
                .document(user.userEmail!!)
                .set(user).await()
            onSuccess()
        } catch (exception: Exception) {
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
        try {
            val authResultTask = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResultTask.user!!
            coroutineScope.launch {
                user.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                ).await()

                try {
                    createUserFirestoreData(user = UserProfileData(
                        displayName = name,
                        photoURL = user.photoUrl.toString(),
                        userEmail = email,
                        phone = user.phoneNumber,
                    ),
                        onSuccess = {}, onFailure = {})
                    onSuccessListener()
                } catch (exception: Exception) {
                    onErrorListener(exception)
                }
            }
        } catch (exception: Exception) {
            onErrorListener(exception)
        }
    }

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

    override fun getUserProfileData(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?> = callbackFlow {

        val snapshotListener = firestore.collection(FirebaseDirectories.AdminsCollection.name)
            .document(auth.currentUser?.email!!)
            .addSnapshotListener { value, error ->

                if (error != null) {
                    onFailure(error)
                    close(error) // Close the flow with an error
                } else {
                    if (value?.data.isNullOrEmpty()) { // Document does not exist
                        coroutineScope.launch {
                            val user = auth.currentUser!!

                            createUserFirestoreData(
                                user = UserProfileData(
                                    displayName = user.displayName,
                                    photoURL = user.photoUrl.toString(),
                                    userEmail = user.email,
                                    phone = user.phoneNumber,
                                ),
                                onSuccess = {},
                                onFailure = {}
                            )
                        }
                    } else {
                        val userProfileData = value?.data.let {
                            UserProfileData(
                                displayName = it?.get("displayName").toString(),
                                photoURL = it?.get("photoURL").toString(),
                                userEmail = it?.get("userEmail").toString(),
                                phone = it?.get("phone").toString(),
                            )
                        }

                        trySend(userProfileData) // Offer the latest DocumentSnapshot
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
        try {
            auth.sendPasswordResetEmail(email).await()
            onSuccess()
        } catch (exception: Exception) {
            onFailure(exception)
        }
    }

    override suspend fun updateUserProfile(
        userProfileDate: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {

        try {
            firestore.collection(FirebaseDirectories.AdminsCollection.name)
                .document(userProfileDate.userEmail!!)
                .set(userProfileDate).await()

            auth.currentUser?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(userProfileDate.displayName)
                    .setPhotoUri(Uri.parse(userProfileDate.photoURL))
                    .build()
            )?.await()

            // Reload the user profile to ensure that changes are reflected immediately
            auth.currentUser?.reload()?.await()

            onSuccess()
        } catch (exception: Exception) {
            onFailure(exception)
        }
    }


    override suspend fun signOut() {
        auth.signOut()
    }

    override fun getAdminsList(
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<UserProfileData?>?> = callbackFlow {
        val snapshotListener = firestore.collection(FirebaseDirectories.AdminsCollection.name)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e(TAG, "getAdminsList: ", error)
                    close(error)
                } else {
                    var admins: List<UserProfileData?>? = emptyList()
                    if (!value?.documents.isNullOrEmpty()) {
                        admins = value?.documents?.map { documentSnapshot: DocumentSnapshot? ->
                            documentSnapshot?.data.let {
                                UserProfileData(
                                    displayName = it?.get("displayName").toString(),
                                    photoURL = it?.get("photoURL").toString(),
                                    userEmail = it?.get("userEmail").toString(),
                                    phone = it?.get("phone").toString(),
                                )
                            }
                        }
                    }
                    trySend(admins)
                }
            }
        awaitClose { snapshotListener.remove() }
    }
}
