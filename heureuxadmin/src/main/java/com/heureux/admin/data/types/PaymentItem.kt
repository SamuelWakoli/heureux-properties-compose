package com.heureux.admin.data.types

data class PaymentItem(
    val paymentId: String,
    val userId: String,
    val amount: String,
    val agreedPrice: String,
    val totalPrice: String,
    val owingAmount: String,
    val paymentMethod: String,
    val time: String,
    val approvedBy: String,
)
