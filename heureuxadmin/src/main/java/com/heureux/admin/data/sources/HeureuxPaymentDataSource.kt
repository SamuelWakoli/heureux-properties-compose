package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.heureux.admin.data.FirebaseDirectories
import com.heureux.admin.data.types.PaymentItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HeureuxPaymentDataSource : PaymentsDataSource {
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    override fun getAllPayments(onError: (exception: Exception) -> Unit): Flow<List<PaymentItem>> =
        callbackFlow {
            val snapshotListener = firestore.collection(FirebaseDirectories.PaymentsCollection.name)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        onError(error)
                        close(error)
                    } else {
                        val payments: MutableList<PaymentItem>? = null
                        value?.documents?.forEach { document ->
                            payments?.add(
                                PaymentItem(
                                    paymentId = document.id,
                                    propertyId = document.get("propertyId").toString(),
                                    userId = document.get("userId").toString(),
                                    amount = document.get("amount").toString(),
                                    agreedPrice = document.get("agreedPrice").toString(),
                                    totalAmountPaid = document.get("totalAmountPaid").toString(),
                                    owingAmount = document.get("owingAmount").toString(),
                                    paymentMethod = document.get("paymentMethod").toString(),
                                    time = document.get("time").toString(),
                                    approvedBy = document.get("approvedBy").toString(),
                                )
                            )

                        }
                    }
                }

            awaitClose { snapshotListener.remove() }
        }

    override fun getPayment(
        id: String, onError: (exception: Exception) -> Unit
    ): Flow<PaymentItem> = callbackFlow {
        val snapshotListener =
            firestore.collection(FirebaseDirectories.PaymentsCollection.name).document(id)
                .addSnapshotListener { document, error ->
                    if (error != null) {
                        onError(error)
                        close(error)
                    } else {
                        if (document?.data != null) {
                            val payment = PaymentItem(
                                paymentId = document.id,
                                propertyId = document.get("propertyId").toString(),
                                userId = document.get("userId").toString(),
                                amount = document.get("amount").toString(),
                                agreedPrice = document.get("agreedPrice").toString(),
                                totalAmountPaid = document.get("totalAmountPaid").toString(),
                                owingAmount = document.get("owingAmount").toString(),
                                paymentMethod = document.get("paymentMethod").toString(),
                                time = document.get("time").toString(),
                                approvedBy = document.get("approvedBy").toString(),
                            )
                            trySend(payment)
                        }
                    }
                }

        awaitClose { snapshotListener.remove() }
    }

    override suspend fun addPayment(
        payment: PaymentItem, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.PaymentsCollection.name)
            .document(payment.paymentId).set(payment).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onError(it)
            }
    }

    override suspend fun updatePayment(
        payment: PaymentItem, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.PaymentsCollection.name)
            .document(payment.paymentId).set(payment).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onError(it)
            }
    }

    override suspend fun deletePayment(
        id: String, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit
    ) {
        firestore.collection(FirebaseDirectories.PaymentsCollection.name).document(id).delete()
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onError(it)
            }
    }
}