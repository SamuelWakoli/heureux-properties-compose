package com.heureux.admin.ui.presentation.screens.inquiries_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heureux.admin.data.repositories.InquiriesRepository
import com.heureux.admin.data.repositories.PaymentsRepository
import com.heureux.admin.data.repositories.PropertyRepository
import com.heureux.admin.data.repositories.UsersRepository
import com.heureux.admin.data.types.HeureuxUser
import com.heureux.admin.data.types.InquiryItem
import com.heureux.admin.data.types.PaymentItem
import com.heureux.admin.data.types.SellWithUsRequest
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InquiriesViewModel(
    private val usersRepository: UsersRepository,
    private val inquiriesRepository: InquiriesRepository,
    private val paymentsRepository: PaymentsRepository,
    private val propertiesRepository: PropertyRepository,
) : ViewModel() {

    companion object {
        const val TAG = "InquiriesViewModel"
        const val TIMEOUT_MILLIS = 5_000L
    }

    val allUsers by lazy {
        usersRepository.getAllUsers { }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )
    }

    val allProperties by lazy {
        propertiesRepository.getAllProperties { }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )
    }

    val allPropertyInquiries by lazy {
        inquiriesRepository.getPropertyInquiries { }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )
    }

    val allSellWithUsInquiries by lazy {
        inquiriesRepository.getSellWithUsInquiries { }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )
    }


    fun getUserData(id: String, onFailure: (Exception) -> Unit): HeureuxUser? {
        var user: HeureuxUser? = null
        try {
            allUsers.value?.forEach {
                if (it.email == id) {
                    user = it
                }
            }
        } catch (e: Exception) {
            onFailure(e)
        }
        return user
    }

    fun getPropertyData(id: String, onFailure: (Exception) -> Unit): String? {
        var property: String? = null
        try {
            allProperties.value?.forEach {
                if (it.id == id) {
                    property = it.name
                }
            }
        } catch (e: Exception) {
            onFailure(e)
        }
        return property
    }

    fun addPayment(
        payment: PaymentItem,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            paymentsRepository.addPayment(
                payment,
                onSuccess,
                onFailure
            )
        }
    }

    fun updateArchivePropertyInquiry(
        inquiryItem: InquiryItem,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            inquiriesRepository.updateArchivePropertyInquiry(
                inquiryItem,
                onSuccess,
                onFailure
            )
        }
    }

    fun updateArchiveSellWithUsInquiry(
        sellWithUsRequest: SellWithUsRequest,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            inquiriesRepository.updateArchiveSellWithUsInquiry(
                sellWithUsRequest,
                onSuccess,
                onFailure,
            )
        }
    }

    fun deletePropertyInquiry(
        inquiryItem: InquiryItem,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            inquiriesRepository.deletePropertyInquiry(
                inquiryItem,
                onSuccess,
                onFailure
            )
        }
    }

    fun deleteSellWithUsInquiry(
        sellWithUsRequest: SellWithUsRequest,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            inquiriesRepository.deleteSellWithUsInquiry(
                sellWithUsRequest,
                onSuccess,
                onFailure
            )
        }
    }


}