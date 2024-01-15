package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.heureux.admin.data.types.PaymentItem
import kotlinx.coroutines.flow.Flow

interface PaymentsDataSource {
    val firestore: FirebaseFirestore

    fun getAllPayments(
        onError: (exception: Exception) -> Unit,
    ): Flow<List<PaymentItem>>

    fun getPayment(
        id: String, onError: (exception: Exception) -> Unit
    ): Flow<PaymentItem>

    suspend fun addPayment(
        payment: PaymentItem,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    )

    suspend fun updatePayment(
        payment: PaymentItem,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    )

    suspend fun deletePayment(
        id: String,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    )
}