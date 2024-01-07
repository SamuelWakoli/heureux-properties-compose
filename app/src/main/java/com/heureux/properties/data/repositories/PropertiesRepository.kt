package com.heureux.properties.data.repositories

import android.net.Uri
import com.heureux.properties.data.types.FeedbackItem
import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.data.types.InquiryItem
import com.heureux.properties.data.types.NotificationItem
import com.heureux.properties.data.types.PaymentItem
import com.heureux.properties.data.types.SellWithUsRequest
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


    suspend fun uploadImageGetUrl(
        uri: Uri,
        directory:String,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) : String?

    suspend fun submitInquiry(
        inquiryItem: InquiryItem,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )


    suspend fun sendFeedback(
        feedback: FeedbackItem,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    suspend fun updateBookmarkProperty(
        email: String,
        property: HeureuxProperty,
        onFailure: (exception: Exception) -> Unit,
    )

    suspend fun sendSellWithUsRequest(
        sellWithUsRequest: SellWithUsRequest,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    )
}