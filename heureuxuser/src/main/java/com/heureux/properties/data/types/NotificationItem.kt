package com.heureux.properties.data.types

/**
 * Represents a notification item.
 *
 * @property time The time the notification was received.
 * @property title The title of the notification.
 * @property description The description of the notification.
 * @property isRead Whether the notification has been read.
 */
data class NotificationItem(
    val time: String,
    val title: String,
    val description: String,
    val isRead: Boolean,
)
