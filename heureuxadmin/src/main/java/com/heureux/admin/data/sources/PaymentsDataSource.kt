package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.heureux.admin.data.types.PaymentItem
import kotlinx.coroutines.flow.Flow

/**
 * An interface that provides methods for interacting with payment data.
 */
interface PaymentsDataSource {
    val firestore: FirebaseFirestore

    /**
     * Gets all payments from the data source.
     *
     * @param onError A callback function that will be invoked if an error occurs.
     * @return A Flow that emits a list of PaymentItem objects.
     */
    fun getAllPayments(
        onError: (exception: Exception) -> Unit,
    ): Flow<List<PaymentItem>>

    /**
     * Gets a payment by its ID.
     *
     * @param id The ID of the payment to get.
     * @param onError A callback function that will be invoked if an error occurs.
     * @return A Flow that emits a PaymentItem object.
     */
    fun getPayment(
        id: String, onError: (exception: Exception) -> Unit
    ): Flow<PaymentItem>

    /**
     * Adds a new payment to the data source.
     *
     * @param payment The PaymentItem object to add.
     * @param onSuccess A callback function that will be invoked if the operation is successful.
     * @param onError A callback function that will be invoked if an error occurs.
     */
    suspend fun addPayment(
        payment: PaymentItem,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    )

    /**
     * Updates an existing payment in the data source.
     *
     * @param payment The PaymentItem object to update.
     * @param onSuccess A callback function that will be invoked if the operation is successful.
     * @param onError A callback function that will be invoked if an error occurs.
     */
    suspend fun updatePayment(
        payment: PaymentItem,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    )

    /**
     * Deletes a payment from the data source.
     *
     * @param id The ID of the payment to delete.
     * @param onSuccess A callback function that will be invoked if the operation is successful.
     * @param onError A callback function that will be invoked if an error occurs.
     */
    suspend fun deletePayment(
        id: String,
        onSuccess: () -> Unit,
        onError: (exception: Exception) -> Unit,
    )
}