package com.heureux.admin.data.repositories

import com.heureux.admin.data.sources.PaymentsDataSource
import com.heureux.admin.data.types.PaymentItem
import kotlinx.coroutines.flow.Flow

class HeureuxPaymentsRepository(private val dataSource: PaymentsDataSource) : PaymentsRepository {
    override fun getAllPayments(onError: (exception: Exception) -> Unit): Flow<List<PaymentItem>> =
        dataSource.getAllPayments(onError)

    override fun getPayment(
        id: String, onError: (exception: Exception) -> Unit
    ): Flow<PaymentItem> = dataSource.getPayment(id, onError)

    override suspend fun addPayment(
        payment: PaymentItem, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit
    ) = dataSource.addPayment(payment, onSuccess, onError)

    override suspend fun updatePayment(
        payment: PaymentItem, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit
    ) = dataSource.updatePayment(payment, onSuccess, onError)

    override suspend fun deletePayment(
        id: String, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit
    ) = dataSource.deletePayment(id, onSuccess, onError)
}