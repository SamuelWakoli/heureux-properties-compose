package com.heureux.properties.data.sources

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.storage
import com.heureux.properties.data.FireStoreUserFields
import com.heureux.properties.data.FirebaseDirectories
import com.heureux.properties.data.types.FeedbackItem
import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.data.types.InquiryItem
import com.heureux.properties.data.types.NotificationItem
import com.heureux.properties.data.types.PaymentItem
import com.heureux.properties.data.types.SellWithUsRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * A data source that provides access to Heureux properties.
 */
class HeureuxPropertiesDataSource : PropertiesDataSource {

    /**
     * The FirebaseFirestore instance used to access the database.
     */
    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    /// READING DATA

    /**
     * Gets a list of Heureux properties from the database.
     *
     * @param onFailure A callback function that is called if an error occurs.
     * @return A Flow that emits a list of HeureuxProperty objects.
     */
    override fun getHomeProperties(
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = callbackFlow {
        val snapshotListener =
            firestore.collection(FirebaseDirectories.PropertiesCollection.name)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        onFailure(error)
                        close(error)
                    } else {
                        val list = mutableListOf<HeureuxProperty>()
                        value?.documents?.forEach { doc ->
                            list.add(
                                HeureuxProperty(
                                    id = doc.id,
                                    name = doc.get("name").toString(),
                                    price = doc.get("price").toString(),
                                    location = doc.get("location").toString(),
                                    sellerId = doc.get("sellerId").toString(),
                                    description = doc.get("description").toString(),
                                    imageUrls = doc.get("imageUrls") as List<String>,
                                    purchasedBy = doc.get("purchasedBy").toString()
                                )
                            )
                        }
                        trySend(list)
                    }
                }

        awaitClose { snapshotListener.remove() }
    }

    /**
     * Retrieves the payment history for a given email.
     *
     * @param email The email address of the user.
     * @param onFailure A callback function that is invoked if an error occurs.
     * @return A Flow that emits a list of PaymentItem objects.
     */
    override fun getPaymentHistory(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<PaymentItem>> = callbackFlow {
        val snapshotListener = firestore.collection(FirebaseDirectories.PaymentsCollection.name)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    onFailure(error)
                } else {
                    val payments: MutableList<PaymentItem> = mutableListOf()

                    value.let { snapshot ->
                        snapshot?.documents?.forEach { document ->
                            payments.add(
                                PaymentItem(
                                    paymentId = document.id,
                                    propertyId = document.get("propertyId").toString(),
                                    userId = document.get("userId").toString(),
                                    amount = document.get("amount").toString(),
                                    agreedPrice = document.get("agreedPrice").toString(),
                                    totalAmountPaid = document.get("totalAmountPaid").toString(),
                                    owingAmount = document.get("owingAmount").toString(),
                                    paymentMethod = document.get("paymentMethod").toString(),
                                    time = document.get("time").toString(),
                                    approvedBy = document.get("approvedBy").toString(),
                                )
                            )
                        }
                    }
                    val myPayments = payments.filter { it.userId == email }

                    trySend(myPayments)
                }
            }

        awaitClose { snapshotListener.remove() }
    }

    /**
     * Retrieves the bookmarks for a given email.
     *
     * @param email The email address of the user.
     * @param onFailure A callback function that is invoked if an error occurs.
     * @return A Flow that emits a list of HeureuxProperty objects.
     */
    override fun getBookmarks(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = callbackFlow {
        val snapshotListener =
            firestore.collection(FirebaseDirectories.UsersCollection.name).document(email)
                .collection(
                    FireStoreUserFields.BookmarksCollection.field
                ).addSnapshotListener { value, error ->
                    if (error != null) {
                        close(error)
                        onFailure(error)
                    } else {
                        val bookmarks: MutableList<HeureuxProperty> = mutableListOf()
                        value.let { snapshot ->
                            snapshot?.documents?.forEach { document ->
                                bookmarks.add(
                                    HeureuxProperty(
                                        id = document.id,
                                        name = document.get("name").toString(),
                                        price = document.get("price").toString(),
                                        location = document.get("location").toString(),
                                        sellerId = document.get("sellerId").toString(),
                                        description = document.get("description").toString(),
                                        imageUrls = document.get("imageUrls") as List<String>,
                                        purchasedBy = document.get("purchasedBy").toString()
                                    )
                                )
                            }
                        }
                        trySend(bookmarks)
                    }
                }
        awaitClose { snapshotListener.remove() }
    }

    /**
     * Retrieves notifications for the given email.
     *
     * @param email The email address of the user.
     * @param onFailure A callback function that is invoked if an error occurs.
     * @return A Flow that emits a list of NotificationItem objects.
     */
    override fun getNotifications(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<NotificationItem>> = callbackFlow {
        val snapshotListener =
            firestore.collection(FirebaseDirectories.UsersCollection.name).document(email)
                .collection(FireStoreUserFields.NotificationsCollection.field)
                .addSnapshotListener { value, error ->

                    if (error != null) {
                        close(error)
                        onFailure(error)
                    } else {
                        val notifications: MutableList<NotificationItem> = mutableListOf()

                        value.let { snapshot ->
                            snapshot?.documents?.forEach { document ->
                                notifications.add(
                                    NotificationItem(
                                        time = document.get("time").toString(),
                                        title = document.get("title").toString(),
                                        description = document.get("description").toString(),
                                        isRead = document.get("isRead") as Boolean
                                    )
                                )
                            }
                        }

                        trySend(notifications)
                    }
                }

        awaitClose { snapshotListener.remove() }
    }

    /**
     * Retrieves inquiries for the given email.
     *
     * @param email The email address of the user.
     * @param onFailure A callback function that is invoked if an error occurs.
     * @return A Flow that emits a list of InquiryItem objects.
     */
    override fun getMyInquires(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<InquiryItem>> = callbackFlow {
        val snapshotListener = firestore.collection(FirebaseDirectories.InquiresCollection.name)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    onFailure(error)
                } else {
                    val inquires: MutableList<InquiryItem> = mutableListOf()
                    value.let { snapshot ->
                        snapshot?.documents?.forEach { document ->
                            inquires.add(
                                InquiryItem(
                                    id = document.id,
                                    time = document.get("time").toString(),
                                    propertyId = document.get("propertyId").toString(),
                                    senderId = document.get("senderId").toString(),
                                    offerAmount = document.get("offerAmount").toString(),
                                    preferredPaymentMethod = document.get("preferredPaymentMethod")
                                        .toString(),
                                    phoneNumber = document.get("phoneNumber").toString(),
                                    archived = document.get("archived") as Boolean,
                                )
                            )
                        }

                    }

                    trySend(inquires.filter { it.senderId == email })
                }
            }

        awaitClose { snapshotListener.remove() }
    }

    /**
     * Retrieves a HeureuxProperty object for the given propertyId.
     *
     * @param propertyId The ID of the property.
     * @return A Flow that emits a HeureuxProperty object.
     */
    override fun getPropertyItem(
        propertyId: String,
    ): Flow<HeureuxProperty> = callbackFlow {
        val snapshotListener =
            firestore.collection(FirebaseDirectories.PropertiesCollection.name).document(propertyId)
                .addSnapshotListener { document, error ->
                    if (error != null) {
                        close(error)
                    } else {
                        if (!document?.data.isNullOrEmpty()) {
                            trySend(
                                HeureuxProperty(
                                    id = document!!.id,
                                    name = document.get("name").toString(),
                                    price = document.get("price").toString(),
                                    location = document.get("location").toString(),
                                    sellerId = document.get("sellerId").toString(),
                                    description = document.get("description").toString(),
                                    imageUrls = document.get("imageUrls") as List<String>,
                                    purchasedBy = document.get("purchasedBy").toString()
                                )
                            )
                        }
                    }
                }

        awaitClose { snapshotListener.remove() }
    }


    /// WRITING DATA

    /**
     * Uploads an image to Firebase Storage and returns the download URL.
     *
     * @param uri The URI of the image to upload.
     * @param directory The directory in Firebase Storage to upload the image to.
     * @param onSuccessListener A callback function that will be invoked with the download URL of the uploaded image.
     * @param onFailure A callback function that will be invoked if the upload fails.
     */
    override suspend fun uploadImageGetUrl(
        uri: Uri,
        directory: String,
        onSuccessListener: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        val storage = com.google.firebase.Firebase.storage
        val storageReference = storage.reference.child(directory)
        val uploadTask = storageReference.putFile(uri)

        // Wait for the upload to complete, then get the download URL
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw task.exception!!
            }
            storageReference.downloadUrl
        }.addOnSuccessListener {
            onSuccessListener.invoke(it.toString())
        }.addOnFailureListener {
            onFailure.invoke(it)
        }
    }


    /**
     * Submits an inquiry to Firebase Firestore.
     *
     * @param inquiryItem The inquiry item to submit.
     * @param onSuccessListener A callback function that will be invoked when the inquiry is successfully submitted.
     * @param onFailure A callback function that will be invoked if the submission fails.
     */
    override suspend fun submitInquiry(
        inquiryItem: InquiryItem,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {

        firestore.collection(FirebaseDirectories.InquiresCollection.name).document(inquiryItem.id)
            .set(
                inquiryItem
            ).addOnSuccessListener {
                onSuccessListener()
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    /**
     * Sends feedback to Firebase Firestore.
     *
     * @param feedback The feedback item to send.
     * @param onSuccessListener A callback function that will be invoked when the feedback is successfully sent.
     * @param onFailure A callback function that will be invoked if the sending fails.
     */
    override suspend fun sendFeedback(
        feedback: FeedbackItem,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        val data = hashMapOf(
            "message" to feedback.message,
            "time" to feedback.time,
            "senderEmail" to feedback.senderEmail,
        )

        firestore.collection(FirebaseDirectories.FeedbacksCollection.name).document(feedback.time)
            .set(
                data
            ).addOnSuccessListener {
                onSuccessListener()
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    /**
     * Updates the bookmark property for the given user.
     *
     * @param email The email of the user.
     * @param property The property to update.
     * @param onFailure A callback function that is invoked if the operation fails.
     */
    override suspend fun updateBookmarkProperty(
        email: String,
        property: HeureuxProperty,
        onFailure: (exception: Exception) -> Unit,
    ) {
        try {
            val userBookmarksRef =
                firestore.collection(FirebaseDirectories.UsersCollection.name).document(email)
                    .collection(FireStoreUserFields.BookmarksCollection.field)

            // Check if the property is already in bookmarks
            val existingDocument = userBookmarksRef.document(property.id).get().await()

            if (!existingDocument.exists()) {

                // Add the property to the bookmarks sub-collection
                userBookmarksRef.document(property.id).set(property).await()
            } else {
                // Property is already bookmarked, remove it
                userBookmarksRef.document(property.id).delete().await()
            }

        } catch (exception: Exception) {
            onFailure(exception)
        }
    }

    /**
     * Sends a sell with us request.
     *
     * @param sellWithUsRequest The sell with us request.
     * @param onSuccessListener A callback function that is invoked if the operation succeeds.
     * @param onFailure A callback function that is invoked if the operation fails.
     */
    override suspend fun sendSellWithUsRequest(
        sellWithUsRequest: SellWithUsRequest,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        firestore.collection(FirebaseDirectories.SellWithUsCollection.name)
            .document(sellWithUsRequest.time).set(sellWithUsRequest)
            .addOnSuccessListener {
                onSuccessListener()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}