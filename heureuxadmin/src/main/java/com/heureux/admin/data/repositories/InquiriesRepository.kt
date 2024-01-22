package com.heureux.admin.data.repositories

import com.heureux.admin.data.types.InquiryItem
import com.heureux.admin.data.types.SellWithUsRequest
import kotlinx.coroutines.flow.Flow

/**
 * An interface for accessing inquiries data.
 */
interface InquiriesRepository {
    /**
     * Gets a list of property inquiries.
     *
     * @param onError A callback to be invoked if an error occurs.
     * @return A Flow that emits a list of InquiryItem objects.
     */
    fun getPropertyInquiries(onError: (Exception) -> Unit): Flow<List<InquiryItem>>

    /**
     * Gets a list of sell with us inquiries.
     *
     * @param onError A callback to be invoked if an error occurs.
     * @return A Flow that emits a list of SellWithUsRequest objects.
     */
    fun getSellWithUsInquiries(onError: (Exception) -> Unit): Flow<List<SellWithUsRequest>>

    /**
     * Updates the archive status of a property inquiry.
     *
     * @param data The [InquiryItem] object to update.
     * @param onSuccess A callback to be invoked if the operation is successful.
     * @param onError A callback to be invoked if an error occurs.
     */
    suspend fun updateArchivePropertyInquiry(
        data: InquiryItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    /**
     * Deletes a property inquiry.
     *
     * @param data The [InquiryItem] object to delete.
     * @param onSuccess A callback to be invoked if the operation is successful.
     * @param onError A callback to be invoked if an error occurs.
     */
    suspend fun deletePropertyInquiry(
        data: InquiryItem,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    /**
     * Updates the archive status of a sell with us inquiry.
     *
     * @param data The [SellWithUsRequest] object to update.
     * @param onSuccess A callback to be invoked if the operation is successful.
     * @param onError A callback to be invoked if an error occurs.
     */
    suspend fun updateArchiveSellWithUsInquiry(
        data: SellWithUsRequest,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    /**
     * Deletes a sell with us inquiry.
     *
     * @param data The [SellWithUsRequest] object to delete.
     * @param onSuccess A callback to be invoked if the operation is successful.
     * @param onError A callback to be invoked if an error occurs.
     */
    suspend fun deleteSellWithUsInquiry(
        data: SellWithUsRequest,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )
}