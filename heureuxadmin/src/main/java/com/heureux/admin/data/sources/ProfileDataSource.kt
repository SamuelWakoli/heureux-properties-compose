package com.heureux.admin.data.sources

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.heureux.admin.data.types.UserProfileData
import kotlinx.coroutines.flow.Flow


/**
 * An interface that provides methods for interacting with a user profile data source.
 */
interface ProfileDataSource {
    val auth: FirebaseAuth
    val firestore: FirebaseFirestore
    val storageReference: StorageReference

    /**
     * Uploads an image to the specified directory and returns the download URL.
     *
     * @param uri The URI of the image to upload.
     * @param directory The directory to upload the image to.
     * @param onSuccessListener A callback that will be invoked with the download URL if the upload is successful.
     * @param onFailure A callback that will be invoked with the exception if the upload fails.
     */
    suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
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
     * Registers a new user with the specified credentials.
     *
     * @param name The name of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param onSuccessListener A callback that will be invoked if the registration is successful.
     * @param onErrorListener A callback that will be invoked with the exception if the registration fails.
     */
    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    )

    /**
     * Signs in a user with the specified credentials.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param onSuccessListener A callback that will be invoked if the sign-in is successful.
     * @param onErrorListener A callback that will be invoked with the exception if the sign-in fails.
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
     * @param onSuccess A callback that will be invoked if the data is retrieved successfully.
     * @param onFailure A callback that will be invoked with the exception if the data retrieval fails.
     * @return A Flow that emits the user profile data or null if there is no data.
     */
    fun getUserProfileData(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?>

    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email The email address to send the password reset email to.
     * @param onSuccess A callback that will be invoked if the email is sent successfully.
     * @param onFailure A callback that will be invoked with the exception if the email fails to send.
     */
    suspend fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Updates the user profile with the specified data.
     *
     * @param userProfileDate The user profile data to update.
     * @param onSuccess A callback that will be invoked if the update is successful.
     * @param onFailure A callback that will be invoked with the exception if the update fails.
     */
    suspend fun updateUserProfile(
        userProfileDate: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Creates a new user in Firestore.
     *
     * @param user The user data to be created.
     * @param onSuccess A callback function to be executed on success.
     * @param onFailure A callback function to be executed on failure.
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
     * Gets a list of admins.
     *
     * @param onFailure A callback function to be executed on failure.
     * @return A flow of lists of user profiles.
     */
    fun getAdminsList(
        onFailure: (exception: Exception) -> Unit
    ): Flow<List<UserProfileData?>?>
}