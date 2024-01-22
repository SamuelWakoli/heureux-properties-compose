package com.heureux.admin.data.repositories

import android.net.Uri
import com.heureux.admin.data.types.HeureuxProperty
import kotlinx.coroutines.flow.Flow

/**
 * An interface for interacting with a property repository.
 */
interface PropertyRepository {

    /**
     * Uploads an image to the repository and returns its download URL.
     *
     * @param uri The URI of the image to upload.
     * @param directory The directory in the repository to upload the image to.
     * @param onSuccessListener A callback function that will be invoked with the download URL of the uploaded image.
     * @param onFailure A callback function that will be invoked if an error occurs while uploading the image.
     */
    suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    /**
     * Gets property data for the given property ID.
     *
     * @param id The ID of the property to get data for.
     * @param onFailure A callback function that will be invoked if an error occurs while getting the property data.
     * @return A Flow that emits the property data.
     */
    fun getPropertyData(
        id: String,
        onFailure: (exception: Exception) -> Unit
    ): Flow<HeureuxProperty>

    /**
     * Gets a list of all properties.
     *
     * @param onFailure A callback function that will be invoked if an error occurs while getting the list of properties.
     * @return A Flow that emits the list of properties.
     */
    fun getAllProperties(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxProperty>>

    /**
     * Adds a new property to the repository.
     *
     * @param onSuccess A callback function that will be invoked if the property is added successfully.
     * @param onFailure A callback function that will be invoked if an error occurs while adding the property.
     * @param data The property data to add.
     */
    suspend fun addProperty(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
        data: HeureuxProperty
    )

    /**
     * Updates an existing property in the repository.
     *
     * @param onSuccess A callback function that will be invoked if the property is updated successfully.
     * @param onFailure A callback function that will be invoked if an error occurs while updating the property.
     * @param data The property data to update.
     */
    suspend fun updateProperty(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
        data: HeureuxProperty
    )

    /**
     * Deletes a property from the repository.
     *
     * @param onSuccess A callback function that will be invoked if the property is deleted successfully.
     * @param onFailure A callback function that will be invoked if an error occurs while deleting the property.
     * @param id The ID of the property to delete.
     */
    suspend fun deleteProperty(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
        id: String
    )
}