package com.heureux.properties.data.repositories

import com.google.firebase.auth.FirebaseUser
import com.heureux.properties.data.sources.ProfileDataSource
import com.heureux.properties.data.types.UserProfileData
import kotlinx.coroutines.flow.Flow

class HeureuxProfileRepository(val dataSource: ProfileDataSource) : ProfileRepository {
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
        name: String,
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    ) {
        dataSource.signIn(
            name = name,
            email = email,
            password = password,
            onSuccessListener = onSuccessListener,
            onErrorListener = onErrorListener
        )
    }

    override fun getUserProfileData(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<UserProfileData?> {
        return dataSource.getUserProfileData(
            user = user,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override suspend fun sendPasswordResetEmail(
        userProfileDate: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        dataSource.sendPasswordResetEmail(
            userProfileDate = userProfileDate,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override suspend fun updateUserProfile(
        userProfileDate: UserProfileData,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        dataSource.updateUserProfile(
            userProfileDate = userProfileDate,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override suspend fun createUserFirestoreData(
        user: FirebaseUser,
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

    override suspend fun deleteUserAndData(
        userProfileDate: UserProfileData,
        onSuccessListener: () -> Unit,
        onErrorListener: (exception: Exception) -> Unit,
    ) {
        dataSource.deleteUserAndData(
            userProfileDate = userProfileDate,
            onSuccessListener = onSuccessListener,
            onErrorListener = onErrorListener
        )
    }
}