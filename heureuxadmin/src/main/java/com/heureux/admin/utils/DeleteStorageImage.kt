package com.heureux.admin.utils

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

/**
 * Deletes an image from the given URI.
 *
 * @param uri The URI of the image to delete.
 * @param onSuccess A callback function that will be called if the image is deleted successfully.
 * @param onFailure A callback function that will be called if the image deletion fails.
 */
fun deleteImageFromUri(
    uri: String,
    onSuccess: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
) {
    val storage = Firebase.storage
    val imageRef = storage.getReferenceFromUrl(uri)

    imageRef.delete().addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener {
        onFailure(it)
    }
}