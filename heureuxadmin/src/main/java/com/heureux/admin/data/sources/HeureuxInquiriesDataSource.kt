package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.heureux.admin.data.FirebaseDirectories
import com.heureux.admin.data.types.InquiryItem
import com.heureux.admin.data.types.SellWithUsRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HeureuxInquiriesDataSource : InquiriesDataSource {
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore


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
                        if (value != null) {
                            val inquiries: MutableList<InquiryItem> = mutableListOf()
                            value.documents.forEach { doc ->
                                inquiries.add(
                                    InquiryItem(
                                        id = doc.id,
                                        time = doc.get("time").toString(),
                                        propertyId = doc.get("propertyId").toString(),
                                        senderId = doc.get("senderId").toString(),
                                        offerAmount = doc.get("offerAmount").toString(),
                                        preferredPaymentMethod = doc.get("preferredPaymentMethod")
                                            .toString(),
                                        phoneNumber = doc.get("phoneNumber").toString(),
                                        archived = doc.get("archived") as Boolean
                                    )
                                )
                            }
                            trySend(inquiries)
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
                            if (value != null) {
                                val requests: MutableList<SellWithUsRequest> = mutableListOf()
                                value.documents.forEach { doc ->
                                    requests.add(
                                        SellWithUsRequest(
                                            id = doc.id,
                                            time = doc.get("time").toString(),
                                            userId = doc.get("userId").toString(),
                                            propertyName = doc.get("propertyName").toString(),
                                            propertyDescription = doc.get("propertyDescription")
                                                .toString(),
                                            propertyPrice = doc.get("propertyPrice").toString(),
                                            propertyImages = doc.get("propertyImages") as List<String>,
                                            contactNumber = doc.get("contactNumber").toString(),
                                            archived = doc.get("archived") as Boolean
                                        )
                                    )
                                }
                                trySend(requests)
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
            .set(data.copy(archived = !data.archived)).addOnSuccessListener {
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
            .set(data.copy(archived = !data.archived))
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