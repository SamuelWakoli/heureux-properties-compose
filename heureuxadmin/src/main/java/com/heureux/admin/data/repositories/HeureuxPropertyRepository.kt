package com.heureux.admin.data.repositories

import android.net.Uri
import com.heureux.admin.data.sources.PropertyDataSource
import com.heureux.admin.data.types.HeureuxProperty
import kotlinx.coroutines.flow.Flow

class HeureuxPropertyRepository(private val dataSource: PropertyDataSource) : PropertyRepository {
    override suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) = dataSource.uploadImageGetUrl(uri, directory, onSuccessListener, onFailure)

    override fun getPropertyData(
        id: String, onFailure: (exception: Exception) -> Unit
    ): Flow<HeureuxProperty> = dataSource.getPropertyData(id, onFailure)

    override fun getAllProperties(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxProperty>> =
        dataSource.getAllProperties(onFailure)

    override suspend fun addProperty(
        onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit, data: HeureuxProperty
    ) = dataSource.addProperty(onSuccess, onFailure, data)

    override suspend fun updateProperty(
        onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit, data: HeureuxProperty
    ) = dataSource.updateProperty(onSuccess, onFailure, data)

    override suspend fun deleteProperty(
        onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit, id: String
    ) = dataSource.deleteProperty(onSuccess, onFailure, id)
}