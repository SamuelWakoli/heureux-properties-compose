package com.heureux.admin.data.repositories

import com.heureux.admin.data.sources.PaymentsDataSource
import com.heureux.admin.data.types.PaymentItem
import kotlinx.coroutines.flow.Flow

/**
 * A repository that provides access to payment data.
 *
 * @param dataSource The data source that provides the payment data.
 */
class HeureuxPaymentsRepository(private val dataSource: PaymentsDataSource) : PaymentsRepository {
    /**
     * Gets all payments.
     *
     * @param onError A callback that will be invoked if an error occurs.
     * @return A flow of a list of payment items.
     */
    override fun getAllPayments(onError: (exception: Exception) -> Unit): Flow<List<PaymentItem>> =
        dataSource.getAllPayments(onError)

    /**
     * Gets a payment by its ID.
     *
     * @param id The ID of the payment.
     * @param onError A callback that will be invoked if an error occurs.
     * @return A flow of a payment item.
     */
    override fun getPayment(
        id: String, onError: (exception: Exception) -> Unit
    ): Flow<PaymentItem> = dataSource.getPayment(id, onError)

    /**
     * Adds a new payment.
     *
     * @param payment The payment item to add.
     * @param onSuccess A callback that will be invoked if the operation is successful.
     * @param onError A callback that will be invoked if an error occurs.
     */
    override suspend fun addPayment(
        payment: PaymentItem, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit
    ) = dataSource.addPayment(payment, onSuccess, onError)

    /**
     * Updates an existing payment.
     *
     * @param payment The payment item to update.
     * @param onSuccess A callback that will be invoked if the operation is successful.
     * @param onError A callback that will be invoked if an error occurs.
     */
    override suspend fun updatePayment(
        payment: PaymentItem, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit
    ) = dataSource.updatePayment(payment, onSuccess, onError)

    /**
     * Deletes a payment by its ID.
     *
     * @param id The ID of the payment to delete.
     * @param onSuccess A callback that will be invoked if the operation is successful.
     * @param onError A callback that will be invoked if an error occurs.
     */
    override suspend fun deletePayment(
        id: String, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit
    ) = dataSource.deletePayment(id, onSuccess, onError)
}