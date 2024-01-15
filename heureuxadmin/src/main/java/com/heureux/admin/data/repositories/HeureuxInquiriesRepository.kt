package com.heureux.admin.data.repositories

import com.heureux.admin.data.sources.InquiriesDataSource
import com.heureux.admin.data.types.InquiryItem
import com.heureux.admin.data.types.SellWithUsRequest
import kotlinx.coroutines.flow.Flow

class HeureuxInquiriesRepository(private val dataSource: InquiriesDataSource) :
    InquiriesRepository {
    override fun getPropertyInquiries(onError: (Exception) -> Unit): Flow<List<InquiryItem>> =
        dataSource.getPropertyInquiries(onError)

    override fun getArchivedPropertyInquiries(onError: (Exception) -> Unit): Flow<List<InquiryItem>> =
        dataSource.getArchivedPropertyInquiries(onError)

    override fun getSellWithUsInquiries(onError: (Exception) -> Unit): Flow<List<SellWithUsRequest>> =
        dataSource.getSellWithUsInquiries(onError)

    override fun getArchivedSellWithUsInquiries(onError: (Exception) -> Unit): Flow<List<SellWithUsRequest>> =
        dataSource.getArchivedSellWithUsInquiries(onError)

    override suspend fun updateArchivePropertyInquiry(
        data: InquiryItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) = dataSource.updateArchivePropertyInquiry(data, onSuccess, onError)

    override suspend fun deletePropertyInquiry(
        data: InquiryItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) = dataSource.deletePropertyInquiry(data, onSuccess, onError)

    override suspend fun updateArchiveSellWithUsInquiry(
        data: SellWithUsRequest,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) = dataSource.updateArchiveSellWithUsInquiry(data, onSuccess, onError)

    override suspend fun deleteSellWithUsInquiry(
        data: SellWithUsRequest,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) = dataSource.deleteSellWithUsInquiry(data, onSuccess, onError)
}