package com.heureux.admin.data

sealed class FirebaseDirectories (val name: String) {
    data object AdminsCollection : FirebaseDirectories(name = "admins")
    data object AdminsStorageReference : FirebaseDirectories(name = "admins")
    data object UsersCollection : FirebaseDirectories(name = "users")
    data object UsersStorageReference : FirebaseDirectories(name = "users")
    data object PropertiesCollection : FirebaseDirectories(name = "properties")
    data object PurchasedPropertiesCollection : FirebaseDirectories(name = "purchased_properties")
    data object SoldPropertiesCollection : FirebaseDirectories(name = "sold_properties")
    data object PaymentsCollection : FirebaseDirectories(name = "payments")
    data object InquiresCollection : FirebaseDirectories(name = "inquires")
    data object FeedbacksCollection : FirebaseDirectories(name = "feedbacks")
    data object SellWithUsCollection : FirebaseDirectories(name = "sell_with_us")
}

sealed class FireStoreUserFields (val field: String) {
    data object PhotoUrl:FireStoreUserFields ("photoUrl")
    data object Name:FireStoreUserFields("name")
    data object Phone:FireStoreUserFields("phone")
    data object BookmarksCollection:FireStoreUserFields("bookmarks")
    data object PropertiesOwned:FireStoreUserFields("propertiesOwned")
    data object Listings: FireStoreUserFields("listings")
    data object NotificationsCollection: FireStoreUserFields("notifications")
}