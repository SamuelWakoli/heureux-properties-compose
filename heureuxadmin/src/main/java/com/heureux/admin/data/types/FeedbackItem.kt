package com.heureux.admin.data.types

data class FeedbackItem(
    val message:String,
    val time: String,
    val senderEmail: String,
    val isRead: Boolean = false,
)
