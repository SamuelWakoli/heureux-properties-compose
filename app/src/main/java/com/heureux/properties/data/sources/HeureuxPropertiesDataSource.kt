package com.heureux.properties.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.heureux.properties.data.FireStoreUserFields
import com.heureux.properties.data.FirebaseDirectories
import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.data.types.InquiryItem
import com.heureux.properties.data.types.NotificationItem
import com.heureux.properties.data.types.PaymentItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HeureuxPropertiesDataSource : PropertiesDataSource {

    override val firestore: FirebaseFirestore
        get() = Firebase.firestore

    override fun getHomeProperties(
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = callbackFlow {
        // Get a reference to the "Properties" collection in Firestore.
        val snapshotListener = firestore.collection(FirebaseDirectories.PropertiesCollection.name)
            .addSnapshotListener { value, error ->
                // If there was an error, call the onFailure callback and close the flow.
                if (error != null) {
                    onFailure(error)
                    close(error)
                } else {
                    // Get the list of properties from the snapshot.
                    val properties = value?.toObjects(HeureuxProperty::class.java) ?: emptyList()

                    // Call the onSuccess callback and send the list of properties to the flow.
                    trySend(properties)
                }
            }

        // When the flow is closed, remove the snapshot listener.
        awaitClose {
            snapshotListener.remove()
        }
    }

    /**
     * Gets the list of properties purchased by the user.
     *
     * @param email The user's email address.
     * @param onFailure A callback that is called when an error occurs while retrieving the list of properties.
     *
     * @return A flow that emits the list of properties purchased by the user.
     */
    override fun getMyProperties(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = callbackFlow {

        val snapshotListener =
            firestore.collection(FirebaseDirectories.PurchasedPropertiesCollection.name)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        onFailure(error)
                        close(error)
                    } else {
                        // Get the list of properties from the snapshot.
                        val properties =
                            value?.toObjects(HeureuxProperty::class.java) ?: emptyList()

                        // Filter the list of properties by the user's email address.
                        val myProperties = properties.filter { it.purchasedBy == email }

                        // Call the onSuccess callback and send the list of properties to the flow.
                        trySend(myProperties)
                    }
                }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getUserSoldProperties(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = callbackFlow {
        val snapshotListener =
            firestore.collection(FirebaseDirectories.SoldPropertiesCollection.name)
                .addSnapshotListener { value, error ->

                    if (error != null) {
                        close(error)
                        onFailure(error)
                    } else {
                        // Get the list of properties from the snapshot.
                        val properties =
                            value?.toObjects(HeureuxProperty::class.java) ?: emptyList()

                        // Filter the list of properties by the user's email address.
                        val soldProperties = properties.filter { it.seller == email }

                        // Call the onSuccess callback and send the list of properties to the flow.
                        trySend(soldProperties)
                    }

                }

        awaitClose {
            snapshotListener.remove()
        }
    }

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
                    val payments = value?.toObjects(PaymentItem::class.java) ?: emptyList()

                    val myPayments = payments.filter { it.userId == email }

                    trySend(myPayments)
                }
            }

        awaitClose { snapshotListener.remove() }
    }

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
                        val bookmarks = value?.toObjects(HeureuxProperty::class.java) ?: emptyList()
                        trySend(bookmarks)
                    }
                }
        awaitClose { snapshotListener.remove() }
    }

    override fun getMyListings(
        email: String,
        onFailure: (exception: Exception) -> Unit,
    ): Flow<List<HeureuxProperty>> = callbackFlow {
        val snapshotListener = firestore.collection(FirebaseDirectories.PropertiesCollection.name)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                    onFailure(error)
                } else {
                    val myListings =
                        value?.toObjects(HeureuxProperty::class.java)?.filter { it.seller == email }
                            ?: emptyList()
                    trySend(myListings)
                }
            }

        awaitClose { snapshotListener.remove() }
    }

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
                        val notifications =
                            value?.toObjects(NotificationItem::class.java) ?: emptyList()

                        trySend(notifications)
                    }
                }

        awaitClose { snapshotListener.remove() }
    }

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
                    val inquires =
                        value?.toObjects(InquiryItem::class.java)?.filter { it.senderId == email }
                            ?: emptyList()
                    trySend(inquires)
                }
            }

        awaitClose { snapshotListener.remove() }
    }

    override fun getPropertyItem(
        propertyId: String,
    ): Flow<HeureuxProperty> = callbackFlow {
        val snapshotListener = firestore.collection(FirebaseDirectories.PropertiesCollection.name)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    close(error)
                } else {
                    val allProperties = value?.toObjects(HeureuxProperty::class.java).orEmpty()

                    val property = allProperties.find { it.id == propertyId }

                    if (property != null) {
                        trySend(property)
                    }

                }
            }

        awaitClose { snapshotListener.remove() }
    }
}