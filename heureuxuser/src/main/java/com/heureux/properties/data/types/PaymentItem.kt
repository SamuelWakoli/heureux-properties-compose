package com.heureux.properties.data.types

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
