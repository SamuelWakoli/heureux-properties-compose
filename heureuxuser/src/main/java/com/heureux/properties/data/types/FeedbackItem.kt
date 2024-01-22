package com.heureux.properties.data.types

/**
 * Represents a feedback item.
 * @property id The unique identifier of the feedback item.
 * @property message The message of the feedback item.
 * @property time The time when the feedback item was sent.
 * @property senderEmail The email address of the sender.
 * @property isRead Whether the feedback item has been read.
 */
data class FeedbackItem(
    val id: String,
    val message:String,
    val time: String,
    val senderEmail: String,
    val isRead: Boolean = false,
)
