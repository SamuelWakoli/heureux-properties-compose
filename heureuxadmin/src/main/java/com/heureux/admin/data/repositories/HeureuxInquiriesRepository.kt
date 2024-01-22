package com.heureux.admin.data.repositories

import com.heureux.admin.data.sources.InquiriesDataSource
import com.heureux.admin.data.types.InquiryItem
import com.heureux.admin.data.types.SellWithUsRequest
import kotlinx.coroutines.flow.Flow

/**
 * A repository that provides access to inquiries data.
 *
 * @param dataSource The data source for inquiries.
 */
class HeureuxInquiriesRepository(private val dataSource: InquiriesDataSource) :
    InquiriesRepository {
    /**
     * Gets a list of property inquiries.
     *
     * @param onError A callback to be invoked if an error occurs.
     * @return A flow of a list of [InquiryItem] objects.
     */
    override fun getPropertyInquiries(onError: (Exception) -> Unit): Flow<List<InquiryItem>> =
        dataSource.getPropertyInquiries(onError)

    /**
     * Gets a list of sell with us inquiries.
     *
     * @param onError A callback to be invoked if an error occurs.
     * @return A flow of a list of [SellWithUsRequest] objects.
     */
    override fun getSellWithUsInquiries(onError: (Exception) -> Unit): Flow<List<SellWithUsRequest>> =
        dataSource.getSellWithUsInquiries(onError)

    /**
     * Updates the archive status of a property inquiry.
     *
     * @param data The [InquiryItem] object to update.
     * @param onSuccess A callback to be invoked if the operation is successful.
     * @param onError A callback to be invoked if an error occurs.
     */
    override suspend fun updateArchivePropertyInquiry(
        data: InquiryItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) = dataSource.updateArchivePropertyInquiry(data, onSuccess, onError)

    /**
     * Deletes a property inquiry.
     *
     * @param data The [InquiryItem] object to delete.
     * @param onSuccess A callback to be invoked if the operation is successful.
     * @param onError A callback to be invoked if an error occurs.
     */
    override suspend fun deletePropertyInquiry(
        data: InquiryItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) = dataSource.deletePropertyInquiry(data, onSuccess, onError)

    /**
     * Updates the archive status of a sell with us inquiry.
     *
     * @param data The [SellWithUsRequest] object to update.
     * @param onSuccess A callback to be invoked if the operation is successful.
     * @param onError A callback to be invoked if an error occurs.
     */
    override suspend fun updateArchiveSellWithUsInquiry(
        data: SellWithUsRequest,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) = dataSource.updateArchiveSellWithUsInquiry(data, onSuccess, onError)

    /**
     * Deletes a sell with us inquiry.
     *
     * @param data The [SellWithUsRequest] object to delete.
     * @param onSuccess A callback to be invoked if the operation is successful.
     * @param onError A callback to be invoked if an error occurs.
     */
    override suspend fun deleteSellWithUsInquiry(
        data: SellWithUsRequest,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) = dataSource.deleteSellWithUsInquiry(data, onSuccess, onError)
}