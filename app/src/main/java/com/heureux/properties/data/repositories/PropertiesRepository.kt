package com.heureux.properties.data.repositories

import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.data.types.InquiryItem
import com.heureux.properties.data.types.NotificationItem
import com.heureux.properties.data.types.PaymentItem
import kotlinx.coroutines.flow.Flow

interface PropertiesRepository {

    fun getHomeProperties(
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>


    fun getMyProperties(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>


    fun getUserSoldProperties(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>


    fun getPaymentHistory(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<PaymentItem>>


    fun getBookmarks(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>


    fun getMyListings(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>


    fun getNotifications(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<NotificationItem>>


    fun getMyInquires(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<InquiryItem>>


    fun getPropertyItem(
        propertyId: String,
    ): Flow<HeureuxProperty>
}