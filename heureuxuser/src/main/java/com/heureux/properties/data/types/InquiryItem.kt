package com.heureux.properties.data.types

data class InquiryItem(
    val id: String,
    val time: String,
    val propertyId: String,
    val senderId: String,
    val offerAmount: String,
    val preferredPaymentMethod: String,
    val phoneNumber: String,
    val archived: Boolean = false,
)
