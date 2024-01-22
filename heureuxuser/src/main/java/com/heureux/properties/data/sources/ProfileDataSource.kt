package com.heureux.properties.data.sources

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.heureux.properties.data.types.UserProfileData
import kotlinx.coroutines.flow.Flow

/**
 * An interface that defines the methods for interacting with a user profile data source.
 */
interface ProfileDataSource {
    /**
     * The Firebase authentication instance.
     */
    val auth: FirebaseAuth

    /**
     * The Firebase firestore instance.
     */
    val firestore: FirebaseFirestore

    /**
     * The Firebase storage reference.
     */
    val storageReference: StorageReference

    /**
     * Uploads an image to Firebase storage and returns the download URL.
     *
     * @param uri The URI of the image to upload.
     * @param directory The directory in Firebase storage to upload the image to.
     * @param onSuccessListener A callback function that will be called when the image has been uploaded successfully.
     * @param onFailure A callback function that will be called if an error occurs while uploading the image.
     */
    suspend fun uploadImageGetUrl(
        uri: Uri,
        directory:String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    /**
     * Gets the current Firebase user.
     *
     * @return A Flow that emits the current Firebase user or null if there is no current user.
     */
    suspend fun getCurrentUser() : Flow<FirebaseUser?>

    /**
     * Registers a new user with Firebase authentication.
     *
     * @param name The name of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param onSuccessListener A callback function that will be called when the user has been registered successfully.
     * @param onErrorListener A callback function that will be called if an error occurs while registering the user.
     */
    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    )

    /**
     * Signs in a user with Firebase authentication.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param onSuccessListener A callback function that will be called when the user has been signed in successfully.
     * @param onErrorListener A callback function that will be called if an error occurs while signing in the user.
     */
    suspend fun signIn(
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    )

    /**
     * Gets the user profile data from Firebase firestore.
     *
     * @param onSuccess A callback function that will be called when the user profile data has been retrieved successfully.
     * @param onFailure A callback function that will be called if an error occurs while retrieving the user profile data.
     * @return A Flow that emits the user profile data or null if there is no user profile data.
     */
    fun getUserProfileData(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?>

    /**
     * Sends a password reset email to the user.
     *
     * @param email The email address of the user.
     * @param onSuccess A callback function that will be called when the password reset email has been sent successfully.
     * @param onFailure A callback function that will be called if an error occurs while sending the password reset email.
     */
    suspend fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Updates the user profile data in Firebase firestore.
     *
     * @param userProfileDate The user profile data to update.
     * @param onSuccess A callback function that will be called when the user profile data has been updated successfully.
     * @param onFailure A callback function that will be called if an error occurs while updating the user profile data.
     */
    suspend fun updateUserProfile(
        userProfileDate: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Creates the user profile data in Firebase firestore.
     *
     * @param user The user profile data to create.
     * @param onSuccess A callback function that will be called when the user profile data has been created successfully.
     * @param onFailure A callback function that will be called if an error occurs while creating the user profile data.
     */
    suspend fun createUserFirestoreData(
        user: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Signs out the current user.
     */
    suspend fun signOut()

    /**
     * Deletes the current user and all associated data.
     *
     * @param email The email address of the user to delete.
     * @param onSuccessListener A callback to be invoked if the operation is successful.
     * @param onErrorListener A callback to be invoked if the operation fails.
     */
    suspend fun deleteUserAndData(
        email: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    )
}