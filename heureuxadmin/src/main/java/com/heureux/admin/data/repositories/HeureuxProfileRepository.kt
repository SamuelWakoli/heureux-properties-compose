package com.heureux.admin.data.repositories

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.heureux.admin.data.sources.ProfileDataSource
import com.heureux.admin.data.types.UserProfileData
import kotlinx.coroutines.flow.Flow

class HeureuxProfileRepository(val dataSource: ProfileDataSource) : ProfileRepository {

    override suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = dataSource.uploadImageGetUrl(
        uri, directory, onSuccessListener, onFailure
    )

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

    override fun getUserProfileData(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?> {
        return dataSource.getUserProfileData(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

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

    override suspend fun signOut() {
        dataSource.signOut()
    }


    override suspend fun getCurrentUser(): Flow<FirebaseUser?> =
        dataSource.getCurrentUser()

    override fun getAdminsList(
        onFailure: (exception: Exception) -> Unit
    ): Flow<List<UserProfileData?>?> =
        dataSource.getAdminsList(onFailure = onFailure)
}