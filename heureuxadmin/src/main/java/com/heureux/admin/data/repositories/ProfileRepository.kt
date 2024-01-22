package com.heureux.admin.data.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.heureux.admin.data.types.UserProfileData
import kotlinx.coroutines.flow.Flow

/**
 * An interface that defines the methods for interacting with user profiles.
 */
interface ProfileRepository {

    /**
     * Uploads an image to the server and returns its download URL.
     *
     * @param uri The URI of the image to upload.
     * @param directory The directory to upload the image to.
     * @param onSuccessListener The callback to invoke when the image has been uploaded successfully.
     * @param onFailure The callback to invoke if an error occurs.
     */
    suspend fun uploadImageGetUrl(
        uri: Uri,
        directory:String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    /**
     * Registers a new user.
     *
     * @param name The name of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param onSuccessListener The callback to invoke when the user has been registered successfully.
     * @param onErrorListener The callback to invoke if an error occurs.
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
    suspend fun getCurrentUser() : Flow<FirebaseUser?>

    /**
     * Signs in a user.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param onSuccessListener The callback to invoke when the user has been signed in successfully.
     * @param onErrorListener The callback to invoke if an error occurs.
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
     * @param onSuccess The callback to invoke when the user profile data has been retrieved successfully.
     * @param onFailure The callback to invoke if an error occurs.
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
     * @param onSuccess The callback to invoke when the password reset email has been sent successfully.
     * @param onFailure The callback to invoke if an error occurs.
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
     * @param onSuccess The callback to invoke when the user profile has been updated successfully.
     * @param onFailure The callback to invoke if an error occurs.
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
     * @param onSuccess The callback to invoke when the user's Firestore data has been created successfully.
     * @param onFailure The callback to invoke if an error occurs.
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
     * Gets the list of admins.
     *
     * @param onFailure The callback to invoke if an error occurs.
     * @return A Flow that emits the list of admins.
     */
    fun getAdminsList(
        onFailure: (exception: Exception) -> Unit
    ): Flow<List<UserProfileData?>?>
}