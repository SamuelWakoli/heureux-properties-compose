package com.heureux.admin.data.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.heureux.admin.data.sources.ProfileDataSource
import com.heureux.admin.data.types.UserProfileData
import kotlinx.coroutines.flow.Flow

/**
 * A repository that handles profile-related operations.
 */
class HeureuxProfileRepository(val dataSource: ProfileDataSource) : ProfileRepository {

    /**
     * Uploads an image and gets the download URL.
     *
     * @param uri The URI of the image to upload.
     * @param directory The directory to upload the image to.
     * @param onSuccessListener The callback to invoke when the upload is successful.
     * @param onFailure The callback to invoke when the upload fails.
     */
    override suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = dataSource.uploadImageGetUrl(
        uri, directory, onSuccessListener, onFailure
    )

    /**
     * Registers a new user.
     *
     * @param name The name of the user.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param onSuccessListener The callback to invoke when the registration is successful.
     * @param onErrorListener The callback to invoke when the registration fails.
     */
    override suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    ) {
        dataSource.registerUser(
            name = name,
            email = email,
            password = password,
            onSuccessListener = onSuccessListener,
            onErrorListener = onErrorListener
        )
    }

    /**
     * Signs in a user.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @param onSuccessListener The callback to invoke when the sign-in is successful.
     * @param onErrorListener The callback to invoke when the sign-in fails.
     */
    override suspend fun signIn(
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    ) {
        dataSource.signIn(
            email = email,
            password = password,
            onSuccessListener = onSuccessListener,
            onErrorListener = onErrorListener
        )
    }

    /**
     * Gets the user profile data.
     *
     * @param onSuccess The callback to invoke when the data is successfully retrieved.
     * @param onFailure The callback to invoke when the data retrieval fails.
     * @return A Flow of UserProfileData objects.
     */
    override fun getUserProfileData(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?> {
        return dataSource.getUserProfileData(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    /**
     * Sends a password reset email.
     *
     * @param email The email of the user to send the reset email to.
     * @param onSuccess The callback to invoke when the email is successfully sent.
     * @param onFailure The callback to invoke when the email sending fails.
     */
    override suspend fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        dataSource.sendPasswordResetEmail(
            email = email,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    /**
     * Updates the user profile.
     *
     * @param userProfileData The updated user profile data.
     * @param onSuccess The callback to invoke when the update is successful.
     * @param onFailure The callback to invoke when the update fails.
     */
    override suspend fun updateUserProfile(
        userProfileData: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        dataSource.updateUserProfile(
            userProfileDate = userProfileData,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    /**
     * Creates the user's Firestore data.
     *
     * @param user The user data to create.
     * @param onSuccess The callback to invoke when the data is successfully created.
     * @param onFailure The callback to invoke when the data creation fails.
     */
    override suspend fun createUserFirestoreData(
        user: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        dataSource.createUserFirestoreData(
            user = user,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    /**
     * Signs out the current user.
     */
    override suspend fun signOut() {
        dataSource.signOut()
    }


    /**
     * Gets the current user.
     *
     * @return A Flow that emits the current FirebaseUser or null if no user is signed in.
     */
    override suspend fun getCurrentUser(): Flow<FirebaseUser?> =
        dataSource.getCurrentUser()

    /**
     * Gets a list of administrators.
     *
     * @param onFailure A callback function that is invoked if an exception occurs.
     * @return A Flow that emits a list of UserProfileData objects representing the administrators.
     */
    override fun getAdminsList(
        onFailure: (exception: Exception) -> Unit
    ): Flow<List<UserProfileData?>?> =
        dataSource.getAdminsList(onFailure = onFailure)
}