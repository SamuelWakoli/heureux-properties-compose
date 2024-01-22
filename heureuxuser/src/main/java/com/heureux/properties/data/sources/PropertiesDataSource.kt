package com.heureux.properties.data.sources

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.heureux.properties.data.types.FeedbackItem
import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.data.types.InquiryItem
import com.heureux.properties.data.types.NotificationItem
import com.heureux.properties.data.types.PaymentItem
import com.heureux.properties.data.types.SellWithUsRequest
import kotlinx.coroutines.flow.Flow
/**
 * An interface that defines the methods for accessing and modifying data.
 */
interface PropertiesDataSource {
    val firestore: FirebaseFirestore

    /// READING DATA

    /**
     * Gets a list of home properties.
     *
     * @param onFailure A callback function that is invoked if an exception occurs.
     * @return A Flow that emits a list of HeureuxProperty objects.
     */
    fun getHomeProperties(
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>


    /**
     * Gets a list of payment history items.
     *
     * @param email The email address of the user.
     * @param onFailure A callback function that is invoked if an exception occurs.
     * @return A Flow that emits a list of PaymentItem objects.
     */
    fun getPaymentHistory(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<PaymentItem>>


    /**
     * Gets a list of bookmarked properties.
     *
     * @param email The email address of the user.
     * @param onFailure A callback function that is invoked if an exception occurs.
     * @return A Flow that emits a list of HeureuxProperty objects.
     */
    fun getBookmarks(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>>



    /**
     * Gets a list of notifications.
     *
     * @param email The email address of the user.
     * @param onFailure A callback function that is invoked if an exception occurs.
     * @return A Flow that emits a list of NotificationItem objects.
     */
    fun getNotifications(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<NotificationItem>>


    /**
     * Gets a list of inquiries.
     *
     * @param email The email address of the user.
     * @param onFailure A callback function that is invoked if an exception occurs.
     * @return A Flow that emits a list of InquiryItem objects.
     */
    fun getMyInquires(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<InquiryItem>>


    /**
     * Gets a property item.
     *
     * @param propertyId The ID of the property.
     * @return A Flow that emits a HeureuxProperty object.
     */
    fun getPropertyItem(
        propertyId: String,
    ): Flow<HeureuxProperty>


    /// WRITING DATA

    /**
     * Uploads an image and gets its URL.
     *
     * @param uri The URI of the image.
     * @param directory The directory to store the image in.
     * @param onSuccessListener A callback function that is invoked if the image is uploaded successfully.
     * @param onFailure A callback function that is invoked if an exception occurs.
     */
    suspend fun uploadImageGetUrl(
        uri: Uri,
        directory:String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    )

    /**
     * Submits an inquiry.
     *
     * @param inquiryItem The inquiry item to submit.
     * @param onSuccessListener A callback function that is invoked if the inquiry is submitted successfully.
     * @param onFailure A callback function that is invoked if an exception occurs.
     */
    suspend fun submitInquiry(
        inquiryItem: InquiryItem,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Sends feedback.
     *
     * @param feedback The feedback item to send.
     * @param onSuccessListener A callback function that is invoked if the feedback is sent successfully.
     * @param onFailure A callback function that is invoked if an exception occurs.
     */
    suspend fun sendFeedback(
        feedback: FeedbackItem,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Updates a bookmarked property.
     *
     * @param email The email address of the user.
     * @param property The property to update.
     * @param onFailure A callback function that is invoked if an exception occurs.
     */
    suspend fun updateBookmarkProperty(
        email: String,
        property: HeureuxProperty,
        onFailure: (exception: Exception) -> Unit,
    )

    /**
     * Sends a Sell With Us request.
     *
     * @param sellWithUsRequest The Sell With Us request to send.
     * @param onSuccessListener The listener to call if the request is successful.
     * @param onFailure The listener to call if the request fails.
     */
    suspend fun sendSellWithUsRequest(
        sellWithUsRequest: SellWithUsRequest,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit
    )
}