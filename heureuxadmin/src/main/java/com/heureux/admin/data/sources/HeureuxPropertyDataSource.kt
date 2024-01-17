package com.heureux.admin.data.sources

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.heureux.admin.data.FirebaseDirectories
import com.heureux.admin.data.types.HeureuxProperty
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HeureuxPropertyDataSource : PropertyDataSource {
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore
    override val storage: FirebaseStorage
        get() = Firebase.storage

    override suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        val storageReference = storage.reference.child(directory)
        val uploadTask = storageReference.putFile(uri)

        // Wait for the upload to complete, then get the download URL
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw task.exception!!
            }
            storageReference.downloadUrl
        }.addOnSuccessListener {
            onSuccessListener.invoke(it.toString())
        }.addOnFailureListener {
            onFailure.invoke(it)
        }
    }

    override fun getPropertyData(
        id: String, onFailure: (exception: Exception) -> Unit
    ): Flow<HeureuxProperty> = callbackFlow {
        val snapshotListener =
            firestore.collection(FirebaseDirectories.PropertiesCollection.name).document(id)
                .addSnapshotListener { document, error ->
                    if (error != null) {
                        onFailure(error)
                        close(error)
                    }
                    if (document != null) {
                        trySend(
                            HeureuxProperty(
                                id = document.id,
                                name = document.get("name").toString(),
                                price = document.get("price").toString(),
                                location = document.get("location").toString(),
                                sellerId = document.get("sellerId").toString(),
                                description = document.get("description").toString(),
                                imageUrls = document.get("imageUrls") as List<String>,
                                purchasedBy = document.get("purchasedBy").toString()
                            )
                        )
                    }
                }

        awaitClose { snapshotListener.remove() }
    }

    override fun getAllProperties(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxProperty>> =
        callbackFlow {
            val snapshotListener =
                firestore.collection(FirebaseDirectories.PropertiesCollection.name)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            onFailure(error)
                            close(error)
                        } else {
                            if (value != null) {
                                val list = mutableListOf<HeureuxProperty>()
                                value.documents.forEach { doc ->
                                    list.add(
                                        HeureuxProperty(
                                            id = doc.id,
                                            name = doc.get("name").toString(),
                                            price = doc.get("price").toString(),
                                            location = doc.get("location").toString(),
                                            sellerId = doc.get("sellerId").toString(),
                                            description = doc.get("description").toString(),
                                            imageUrls = doc.get("imageUrls") as List<String>,
                                            purchasedBy = doc.get("purchasedBy").toString()
                                        )
                                    )
                                }
                                trySend(list)
                            }
                        }
                    }

            awaitClose { snapshotListener.remove() }
        }

    override suspend fun addProperty(
        onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit, data: HeureuxProperty
    ) {
        firestore.collection(FirebaseDirectories.PropertiesCollection.name).document(data.id)
            .set(data).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFailure(it)
            }
    }

    override suspend fun updateProperty(
        onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit, data: HeureuxProperty
    ) {
        firestore.collection(FirebaseDirectories.PropertiesCollection.name).document(data.id)
            .set(data).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFailure(it)
            }
    }

    override suspend fun deleteProperty(
        onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit, id: String
    ) {
        firestore.collection(FirebaseDirectories.PropertiesCollection.name).document(id).delete()
            .addOnSuccessListener {
                // TODO: Delete images from storage
                onSuccess()
            }.addOnFailureListener {
                onFailure(it)
            }
    }
}