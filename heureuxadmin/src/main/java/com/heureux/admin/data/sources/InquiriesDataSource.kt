package com.heureux.admin.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.heureux.admin.data.types.InquiryItem
import com.heureux.admin.data.types.SellWithUsRequest
import kotlinx.coroutines.flow.Flow

interface InquiriesDataSource {
    val firestore: FirebaseFirestore

    fun getPropertyInquiries(onError: (Exception) -> Unit): Flow<List<InquiryItem>>
    fun getArchivedPropertyInquiries(onError: (Exception) -> Unit): Flow<List<InquiryItem>>

    fun getSellWithUsInquiries(onError: (Exception) -> Unit): Flow<List<SellWithUsRequest>>
    fun getArchivedSellWithUsInquiries(onError: (Exception) -> Unit): Flow<List<SellWithUsRequest>>

    suspend fun updateArchivePropertyInquiry(
        data: InquiryItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun deletePropertyInquiry(
        data: InquiryItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun updateArchiveSellWithUsInquiry(
        data: SellWithUsRequest,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun deleteSellWithUsInquiry(
        data: SellWithUsRequest,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )
}