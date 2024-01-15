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
                        val payments = value?.toObjects(PaymentItem::class.java)
                        trySend(payments ?: listOf())
                    }
                }

            awaitClose { snapshotListener.remove() }
        }

    override fun getPayment(
        id: String, onError: (exception: Exception) -> Unit
    ): Flow<PaymentItem> = callbackFlow {
        val snapshotListener =
            firestore.collection(FirebaseDirectories.PaymentsCollection.name).document(id)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        onError(error)
                        close(error)
                    } else {
                        if (value?.data != null) {
                            val payment = value.toObject(PaymentItem::class.java)
                            trySend(payment!!)
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