package com.heureux.properties.data.types

data class FeedbackItem(
    val id: String,
    val message:String,
    val time: String,
    val senderEmail: String,
    val isRead: Boolean = false,
)
