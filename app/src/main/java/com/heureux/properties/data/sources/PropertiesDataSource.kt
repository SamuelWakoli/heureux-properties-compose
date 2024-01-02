package com.heureux.properties.data.sources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.data.types.InquiryItem
import com.heureux.properties.data.types.NotificationItem
import com.heureux.properties.data.types.PaymentItem
import kotlinx.coroutines.flow.Flow

interface PropertiesDataSource {
    val auth: FirebaseAuth
    val firestore: FirebaseFirestore
    val storage: FirebaseStorage


    fun getHomeProperties(
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>


    fun getMyProperties(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>


    fun getSoldProperties(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>


    fun getPaymentHistory(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<PaymentItem>>


    fun getBookmarks(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>


    fun getMyListings(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<HeureuxProperty>

    fun getNotifications(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<NotificationItem>>


    fun getMyInquires(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<InquiryItem>>


    fun getPropertyItem(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<HeureuxProperty>

}