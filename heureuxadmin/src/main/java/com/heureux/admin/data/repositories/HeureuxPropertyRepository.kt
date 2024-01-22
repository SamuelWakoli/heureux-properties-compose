package com.heureux.admin.data.repositories

import android.net.Uri
import com.heureux.admin.data.sources.PropertyDataSource
import com.heureux.admin.data.types.HeureuxProperty
import kotlinx.coroutines.flow.Flow

/**
 * A repository for accessing property data.
 *
 * @param dataSource The data source to use for accessing property data.
 */
class HeureuxPropertyRepository(private val dataSource: PropertyDataSource) : PropertyRepository {
    /**
     * Uploads an image and gets the download URL.
     *
     * @param uri The URI of the image to upload.
     * @param directory The directory to upload the image to.
     * @param onSuccessListener The listener to call when the upload is successful.
     * @param onFailure The listener to call when the upload fails.
     */
    override suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) = dataSource.uploadImageGetUrl(uri, directory, onSuccessListener, onFailure)

    /**
     * Gets the property data for the given ID.
     *
     * @param id The ID of the property to get.
     * @param onFailure The listener to call when the operation fails.
     * @return A Flow that emits the property data.
     */
    override fun getPropertyData(
        id: String, onFailure: (exception: Exception) -> Unit
    ): Flow<HeureuxProperty> = dataSource.getPropertyData(id, onFailure)

    /**
     * Gets all properties.
     *
     * @param onFailure The listener to call when the operation fails.
     * @return A Flow that emits a list of all properties.
     */
    override fun getAllProperties(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxProperty>> =
        dataSource.getAllProperties(onFailure)

    /**
     * Adds a new property.
     *
     * @param onSuccess The listener to call when the operation is successful.
     * @param onFailure The listener to call when the operation fails.
     * @param data The property data to add.
     */
    override suspend fun addProperty(
        onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit, data: HeureuxProperty
    ) = dataSource.addProperty(onSuccess, onFailure, data)

    /**
     * Updates an existing property.
     *
     * @param onSuccess The listener to call when the operation is successful.
     * @param onFailure The listener to call when the operation fails.
     * @param data The property data to update.
     */
    override suspend fun updateProperty(
        onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit, data: HeureuxProperty
    ) = dataSource.updateProperty(onSuccess, onFailure, data)

    /**
     * Deletes a property.
     *
     * @param onSuccess The listener to call when the operation is successful.
     * @param onFailure The listener to call when the operation fails.
     * @param id The ID of the property to delete.
     */
    override suspend fun deleteProperty(
        onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit, id: String
    ) = dataSource.deleteProperty(onSuccess, onFailure, id)
}