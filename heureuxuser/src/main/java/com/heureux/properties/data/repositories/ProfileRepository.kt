package com.heureux.properties.data.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.heureux.properties.data.types.UserProfileData
import kotlinx.coroutines.flow.Flow

/**
 * An interface for interacting with user profiles.
 */
interface ProfileRepository {

    /**
     * Uploads an image to the server and returns its download URL.
     *
     * @param uri The URI of the image to upload.
     * @param directory The directory to upload the image to.
     * @param onSuccessListener A callback function that will be invoked with the download URL if the upload is successful.
     * @param onFailure A callback function that will be invoked with the exception if the upload fails.
     */
    suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    /**
     * Registers a new user.
     *
     * @param name The name of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param onSuccessListener A callback function that will be invoked if the registration is successful.
     * @param onErrorListener A callback function that will be invoked with the exception if the registration fails.
     */
    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    )

    /**
     * Gets the current user.
     *
     * @return A Flow that emits the current user.
     */
    suspend fun getCurrentUser(): Flow<FirebaseUser?>

    /**
     * Signs in a user.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param onSuccessListener A callback function that will be invoked if the sign in is successful.
     * @param onErrorListener A callback function that will be invoked with the exception if the sign in fails.
     */
    suspend fun signIn(
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    )

    /**
     * Gets the user profile data.
     *
     * @param onSuccess A callback function that will be invoked if the data is retrieved successfully.
     * @param onFailure A callback function that will be invoked with the exception if the data retrieval fails.
     * @return A Flow that emits the user profile data.
     */
    fun getUserProfileData(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?>

    /**
     * Sends a password reset email to the user.
     *
     * @param email The email address of the user.
     * @param onSuccess A callback function that will be invoked if the email is sent successfully.
     * @param onFailure A callback function that will be invoked with the exception if the email fails to send.
     */
    suspend fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Updates the user profile.
     *
     * @param userProfileData The updated user profile data.
     * @param onSuccess A callback function that will be invoked if the update is successful.
     * @param onFailure A callback function that will be invoked with the exception if the update fails.
     */
    suspend fun updateUserProfile(
        userProfileData: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Creates the user's Firestore data.
     *
     * @param user The user profile data.
     * @param onSuccess A callback function that will be invoked if the data is created successfully.
     * @param onFailure A callback function that will be invoked with the exception if the data creation fails.
     */
    suspend fun createUserFirestoreData(
        user: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Signs out the user.
     */
    suspend fun signOut()

    /**
     * Deletes the user and their data.
     *
     * @param email The email address of the user.
     * @param onSuccessListener A callback function that will be invoked if the user is deleted successfully.
     * @param onErrorListener A callback function that will be invoked with the exception if the user deletion fails.
     */
    suspend fun deleteUserAndData(
        email: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    )
}