package com.heureux.properties.data.types

/**
 * Represents a payment item.
 *
 * @property paymentId The ID of the payment.
 * @property propertyId The ID of the property associated with the payment.
 * @property userId The ID of the user who made the payment.
 * @property amount The amount of the payment.
 * @property agreedPrice The agreed price of the property.
 * @property totalAmountPaid The total amount paid so far.
 * @property owingAmount The amount still owing.
 * @property paymentMethod The payment method used.
 * @property time The time the payment was made.
 * @property approvedBy The user who approved the payment.
 */
data class PaymentItem(
    val paymentId: String,
    val propertyId: String,
    val userId: String,
    val amount: String,
    val agreedPrice: String,
    val totalAmountPaid: String,
    val owingAmount: String,
    val paymentMethod: String,
    val time: String,
    val approvedBy: String,
)
