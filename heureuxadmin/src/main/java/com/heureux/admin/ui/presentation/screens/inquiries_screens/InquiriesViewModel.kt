package com.heureux.admin.ui.presentation.screens.inquiries_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heureux.admin.data.repositories.InquiriesRepository
import com.heureux.admin.data.repositories.PaymentsRepository
import com.heureux.admin.data.repositories.PropertyRepository
import com.heureux.admin.data.repositories.UsersRepository
import com.heureux.admin.data.types.HeureuxProperty
import com.heureux.admin.data.types.InquiryItem
import com.heureux.admin.data.types.PaymentItem
import com.heureux.admin.data.types.SellWithUsRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SellWithUsInquiryUiState(
    val currentImagesList: List<String> = emptyList(),
)

class InquiriesViewModel(
    private val usersRepository: UsersRepository,
    private val inquiriesRepository: InquiriesRepository,
    private val paymentsRepository: PaymentsRepository,
    private val propertiesRepository: PropertyRepository,
) : ViewModel() {

    private var _sellWithUsInquiryUiState = MutableStateFlow(SellWithUsInquiryUiState())
    val sellWithUsInquiryUiState = _sellWithUsInquiryUiState.asStateFlow()

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

    fun updateCurrentImagesList(imagesList: List<String>) {
        _sellWithUsInquiryUiState.value = _sellWithUsInquiryUiState.value.copy(
            currentImagesList = imagesList
        )
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

    fun updateProperty(
        property: HeureuxProperty,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            propertiesRepository.updateProperty(
                onSuccess = onSuccess,
                onFailure = onFailure,
                data = property
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