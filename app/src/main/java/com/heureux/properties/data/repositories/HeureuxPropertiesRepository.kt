package com.heureux.properties.data.repositories

import com.heureux.properties.data.sources.PropertiesDataSource
import com.heureux.properties.data.types.FeedbackItem
import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.data.types.InquiryItem
import com.heureux.properties.data.types.NotificationItem
import com.heureux.properties.data.types.PaymentItem
import kotlinx.coroutines.flow.Flow

class HeureuxPropertiesRepository(private val dataSource: PropertiesDataSource) :
    PropertiesRepository {

    override fun getHomeProperties(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxProperty>> =
        dataSource.getHomeProperties(onFailure = onFailure)

    override fun getMyProperties(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = dataSource.getMyProperties(
        email = email, onFailure = onFailure
    )

    override fun getUserSoldProperties(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = dataSource.getUserSoldProperties(
        email = email, onFailure = onFailure
    )

    override fun getPaymentHistory(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<PaymentItem>> = dataSource.getPaymentHistory(
        email = email, onFailure = onFailure
    )

    override fun getBookmarks(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = dataSource.getBookmarks(
        email = email, onFailure = onFailure
    )

    override fun getMyListings(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = dataSource.getMyListings(
        email = email, onFailure = onFailure
    )

    override fun getNotifications(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<NotificationItem>> = dataSource.getNotifications(
        email = email, onFailure = onFailure
    )

    override fun getMyInquires(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<InquiryItem>> = dataSource.getMyInquires(
        email = email, onFailure = onFailure
    )

    override fun getPropertyItem(propertyId: String): Flow<HeureuxProperty> =
        dataSource.getPropertyItem(propertyId)


    override suspend fun submitInquiry(
        inquiryItem: InquiryItem,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = dataSource.submitInquiry(
        inquiryItem, onSuccessListener, onFailure
    )

    override suspend fun sendFeedback(
        feedback: FeedbackItem,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = dataSource.sendFeedback(
        feedback, onSuccessListener, onFailure
    )

    override suspend fun updateBookmarkProperty(
        email: String,
        property: HeureuxProperty,
        onFailure: (exception: Exception) -> Unit,
    ) = dataSource.updateBookmarkProperty(
        email = email, property = property, onFailure = onFailure
    )
}