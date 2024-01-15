package com.heureux.admin.data.repositories

import android.net.Uri
import com.heureux.admin.data.types.HeureuxProperty
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {

    suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    fun getPropertyData(
        id: String,
        onFailure: (exception: Exception) -> Unit
    ): Flow<HeureuxProperty>

    fun getAllProperties(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxProperty>>

    suspend fun addProperty(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
        data: HeureuxProperty
    )

    suspend fun updateProperty(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
        data: HeureuxProperty
    )

    suspend fun deleteProperty(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
        id: String
    )
}