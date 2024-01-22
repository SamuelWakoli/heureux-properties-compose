package com.heureux.properties.data.repositories

import android.net.Uri
import com.heureux.properties.data.sources.PropertiesDataSource
import com.heureux.properties.data.types.FeedbackItem
import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.data.types.InquiryItem
import com.heureux.properties.data.types.NotificationItem
import com.heureux.properties.data.types.PaymentItem
import com.heureux.properties.data.types.SellWithUsRequest
import kotlinx.coroutines.flow.Flow

/**
 * A repository that provides access to properties and other data.
 */
class HeureuxPropertiesRepository(private val dataSource: PropertiesDataSource) :
    PropertiesRepository {

    /**
     * Gets a list of home properties.
     *
     * @param onFailure A callback to be invoked if an exception occurs.
     * @return A Flow that emits a list of HeureuxProperty objects.
     */
    override fun getHomeProperties(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxProperty>> =
        dataSource.getHomeProperties(onFailure = onFailure)


    /**
     * Gets a list of payment history items.
     *
     * @param email The email address of the user.
     * @param onFailure A callback to be invoked if an exception occurs.
     * @return A Flow that emits a list of PaymentItem objects.
     */
    override fun getPaymentHistory(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<PaymentItem>> = dataSource.getPaymentHistory(
        email = email, onFailure = onFailure
    )

    /**
     * Gets a list of bookmarked properties.
     *
     * @param email The email address of the user.
     * @param onFailure A callback to be invoked if an exception occurs.
     * @return A Flow that emits a list of HeureuxProperty objects.
     */
    override fun getBookmarks(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = dataSource.getBookmarks(
        email = email, onFailure = onFailure
    )

    /**
     * Gets a list of notifications.
     *
     * @param email The email address of the user.
     * @param onFailure A callback to be invoked if an exception occurs.
     * @return A Flow that emits a list of NotificationItem objects.
     */
    override fun getNotifications(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<NotificationItem>> = dataSource.getNotifications(
        email = email, onFailure = onFailure
    )

    /**
     * Gets a list of inquiries.
     *
     * @param email The email address of the user.
     * @param onFailure A callback to be invoked if an exception occurs.
     * @return A Flow that emits a list of InquiryItem objects.
     */
    override fun getMyInquires(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<InquiryItem>> = dataSource.getMyInquires(
        email = email, onFailure = onFailure
    )

    /**
     * Gets a property item.
     *
     * @param propertyId The ID of the property.
     * @return A Flow that emits a HeureuxProperty object.
     */
    override fun getPropertyItem(propertyId: String): Flow<HeureuxProperty> =
        dataSource.getPropertyItem(propertyId)


    /**
     * Uploads an image and gets the download URL.
     *
     * @param uri The URI of the image.
     * @param directory The directory to upload the image to.
     * @param onSuccessListener A callback to be invoked if the upload is successful.
     * @param onFailure A callback to be invoked if an exception occurs.
     */
    override suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = dataSource.uploadImageGetUrl(
        uri, directory, onSuccessListener, onFailure
    )

    /**
     * Submits an inquiry.
     *
     * @param inquiryItem The inquiry item to submit.
     * @param onSuccessListener A callback to be invoked if the submission is successful.
     * @param onFailure A callback to be invoked if an exception occurs.
     */
    override suspend fun submitInquiry(
        inquiryItem: InquiryItem,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = dataSource.submitInquiry(
        inquiryItem, onSuccessListener, onFailure
    )

    /**
     * Sends feedback.
     *
     * @param feedback The feedback item to send.
     * @param onSuccessListener A callback to be invoked if the feedback is sent successfully.
     * @param onFailure A callback to be invoked if an exception occurs.
     */
    override suspend fun sendFeedback(
        feedback: FeedbackItem,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = dataSource.sendFeedback(
        feedback, onSuccessListener, onFailure
    )

    /**
     * Updates a bookmark property.
     *
     * @param email The email of the user.
     * @param property The property to update.
     * @param onFailure A callback function that is called if the operation fails.
     */
    override suspend fun updateBookmarkProperty(
        email: String,
        property: HeureuxProperty,
        onFailure: (exception: Exception) -> Unit,
    ) = dataSource.updateBookmarkProperty(
        email = email, property = property, onFailure = onFailure
    )

    /**
     * Sends a sell with us request.
     *
     * @param sellWithUsRequest The sell with us request to send.
     * @param onSuccessListener A callback function that is called if the operation succeeds.
     * @param onFailure A callback function that is called if the operation fails.
     */
    override suspend fun sendSellWithUsRequest(
        sellWithUsRequest: SellWithUsRequest,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = dataSource.sendSellWithUsRequest(
        sellWithUsRequest, onSuccessListener, onFailure
    )
}