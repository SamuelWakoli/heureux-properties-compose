package com.heureux.admin.data.sources

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.heureux.admin.data.types.HeureuxProperty
import kotlinx.coroutines.flow.Flow

/**
 * An interface for accessing property data.
 */
interface PropertyDataSource {
    val firestore: FirebaseFirestore
    val storage: FirebaseStorage

    /**
     * Uploads an image to Firebase Storage and gets the download URL.
     *
     * @param uri The URI of the image to upload.
     * @param directory The directory in Firebase Storage to upload the image to.
     * @param onSuccessListener The callback to invoke if the upload is successful.
     * @param onFailure The callback to invoke if the upload fails.
     */
    suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    /**
     * Gets property data for a given property ID.
     *
     * @param id The ID of the property.
     * @param onFailure The callback to invoke if the operation fails.
     * @return A Flow that emits the property data.
     */
    fun getPropertyData(
        id: String,
        onFailure: (exception: Exception) -> Unit
    ): Flow<HeureuxProperty>

    /**
     * Gets a list of all properties.
     *
     * @param onFailure The callback to invoke if the operation fails.
     * @return A Flow that emits a list of all properties.
     */
    fun getAllProperties(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxProperty>>

    /**
     * Adds a new property.
     *
     * @param onSuccess The callback to invoke if the operation is successful.
     * @param onFailure The callback to invoke if the operation fails.
     * @param data The property data to add.
     */
    suspend fun addProperty(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
        data: HeureuxProperty
    )

    /**
     * Updates an existing property.
     *
     * @param onSuccess The callback to invoke if the operation is successful.
     * @param onFailure The callback to invoke if the operation fails.
     * @param data The property data to update.
     */
    suspend fun updateProperty(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
        data: HeureuxProperty
    )

    /**
     * Deletes a property.
     *
     * @param onSuccess The callback to invoke if the operation is successful.
     * @param onFailure The callback to invoke if the operation fails.
     * @param id The ID of the property to delete.
     */
    suspend fun deleteProperty(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
        id: String
    )

}