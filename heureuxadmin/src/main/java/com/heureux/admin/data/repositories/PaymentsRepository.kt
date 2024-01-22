package com.heureux.admin.data.repositories

import com.heureux.admin.data.types.PaymentItem
import kotlinx.coroutines.flow.Flow

/**
 * An interface for interacting with payment data.
 */
interface PaymentsRepository {

    /**
     * Gets all payments.
     *
     * @param onError A callback function to handle errors.
     * @return A Flow of a list of PaymentItem objects.
     */
    fun getAllPayments(
        onError: (exception: Exception) -> Unit,
    ): Flow<List<PaymentItem>>

    /**
     * Gets a payment by its ID.
     *
     * @param id The ID of the payment.
     * @param onError A callback function to handle errors.
     * @return A Flow of a PaymentItem object.
     */
    fun getPayment(
        id: String, onError: (exception: Exception) -> Unit
    ): Flow<PaymentItem>

    /**
     * Adds a new payment.
     *
     * @param payment The PaymentItem object to add.
     * @param onSuccess A callback function to handle success.
     * @param onError A callback function to handle errors.
     */
    suspend fun addPayment(
        payment: PaymentItem,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    )

    /**
     * Updates an existing payment.
     *
     * @param payment The PaymentItem object to update.
     * @param onSuccess A callback function to handle success.
     * @param onError A callback function to handle errors.
     */
    suspend fun updatePayment(
        payment: PaymentItem,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    )

    /**
     * Deletes a payment by its ID.
     *
     * @param id The ID of the payment to delete.
     * @param onSuccess A callback function to handle success.
     * @param onError A callback function to handle errors.
     */
    suspend fun deletePayment(
        id: String,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    )
}