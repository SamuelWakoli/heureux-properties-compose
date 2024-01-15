package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.heureux.admin.data.FirebaseDirectories
import com.heureux.admin.data.types.InquiryItem
import com.heureux.admin.data.types.SellWithUsRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HeureuxInquiriesDataSource : InquiriesDataSource {
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore
    override val storage: FirebaseStorage
        get() = Firebase.storage

    override fun getPropertyInquiries(
        onError: (Exception) -> Unit,
    ): Flow<List<InquiryItem>> =
        callbackFlow {
            val snapshotListener = firestore.collection(FirebaseDirectories.InquiresCollection.name)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        onError(error)
                        close(error)
                    } else {
                        val inquiries = value?.toObjects(InquiryItem::class.java)
                        if (inquiries != null) {
                            trySend(inquiries)
                        }
                    }
                }

            awaitClose { snapshotListener.remove() }
        }

    override fun getArchivedPropertyInquiries(
        onError: (Exception) -> Unit,
    ): Flow<List<InquiryItem>> =
        callbackFlow {
            val snapshotListener = firestore.collection(FirebaseDirectories.InquiresCollection.name)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        onError(error)
                        close(error)
                    } else {
                        val inquiries = value?.toObjects(InquiryItem::class.java)
                        if (inquiries != null) {
                            trySend(inquiries.filter { it.archived })
                        }
                    }
                }

            awaitClose { snapshotListener.remove() }
        }

    override fun getSellWithUsInquiries(
        onError: (Exception) -> Unit,
    ): Flow<List<SellWithUsRequest>> =
        callbackFlow {
            val snapshotListener =
                firestore.collection(FirebaseDirectories.SellWithUsCollection.name)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            onError(error)
                            close(error)
                        } else {
                            val requests = value?.toObjects(SellWithUsRequest::class.java)
                            if (requests != null) {
                                trySend(requests)
                            }
                        }
                    }

            awaitClose { snapshotListener.remove() }
        }

    override fun getArchivedSellWithUsInquiries(
        onError: (Exception) -> Unit,
    ): Flow<List<SellWithUsRequest>> =
        callbackFlow {
            val snapshotListener = firestore.collection(FirebaseDirectories.InquiresCollection.name)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        onError(error)
                        close(error)
                    } else {
                        val requests = value?.toObjects(SellWithUsRequest::class.java)
                        if (requests != null) {
                            trySend(requests.filter { it.archived })
                        }
                    }
                }

            awaitClose { snapshotListener.remove() }
        }


    override suspend fun updateArchivePropertyInquiry(
        data: InquiryItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.InquiresCollection.name).document(data.id)
            .set(data).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                onError(exception)
            }
    }

    override suspend fun deletePropertyInquiry(
        data: InquiryItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.InquiresCollection.name).document(data.id)
            .delete().addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                onError(exception)
            }
    }

    override suspend fun updateArchiveSellWithUsInquiry(
        data: SellWithUsRequest,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.SellWithUsCollection.name).document(data.id)
            .set(data)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                onError(exception)
            }
    }

    override suspend fun deleteSellWithUsInquiry(
        data: SellWithUsRequest,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.SellWithUsCollection.name).document(data.id)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                onError(exception)
            }
    }
}