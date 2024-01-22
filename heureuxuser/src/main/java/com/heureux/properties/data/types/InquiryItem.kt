package com.heureux.properties.data.types

/**
 * Represents an inquiry item.
 *
 * @property id The ID of the inquiry.
 * @property time The time the inquiry was created.
 * @property propertyId The ID of the property associated with the inquiry.
 * @property senderId The ID of the sender of the inquiry.
 * @property offerAmount The offer amount for the property.
 * @property preferredPaymentMethod The preferred payment method for the inquiry.
 * @property phoneNumber The phone number of the sender.
 * @property archived Whether the inquiry is archived.
 */
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
