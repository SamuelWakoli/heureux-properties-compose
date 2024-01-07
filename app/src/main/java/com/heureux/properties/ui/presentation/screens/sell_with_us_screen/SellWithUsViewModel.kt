package com.heureux.properties.ui.presentation.screens.sell_with_us_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heureux.properties.data.repositories.ProfileRepository
import com.heureux.properties.data.repositories.PropertiesRepository
import com.heureux.properties.data.types.SellWithUsRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SellWithUsViewModel(
    private val profileRepository: ProfileRepository,
    private val propertiesRepository: PropertiesRepository,
) : ViewModel() {

    private var _uiState = MutableStateFlow(SellWithUsUiState())
    val uiState = _uiState.asStateFlow()


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val userProfileData =
        profileRepository.getUserProfileData(onSuccess = {}, onFailure = {}).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )

    fun sendSellWithUsRequest(
        sellWithUsRequest: SellWithUsRequest,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) {
        if (uiState.value.propertyName.isEmpty()) {
            _uiState.value = _uiState.value.copy(propertyNameError = true)
        } else if (uiState.value.propertyDescription.isEmpty()) {
            _uiState.value = _uiState.value.copy(propertyDescriptionError = true)
        } else if (uiState.value.propertyPrice.isEmpty()) {
            _uiState.value = _uiState.value.copy(propertyPriceError = true)
        } else if (uiState.value.userPhoneNumber.isEmpty()) {
            _uiState.value = _uiState.value.copy(userPhoneNumberError = true)
        } else if (uiState.value.propertiesImagesCount == 0) {
            _uiState.value = _uiState.value.copy(propertyImagesError = true)
        } else {

            viewModelScope.launch {
                propertiesRepository.sendSellWithUsRequest(
                    sellWithUsRequest = sellWithUsRequest,
                    onSuccessListener = onSuccessListener,
                    onFailure = onFailure
                )
            }
        }
    }

    fun loadUserPhoneNumber() {
        _uiState.value = _uiState.value.copy(userPhoneNumber = userProfileData.value?.phone ?: "")
    }

    fun onPropertyNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(propertyName = name, propertyNameError = false)
    }


    fun onPropertyDescriptionChanged(description: String) {
        _uiState.value =
            _uiState.value.copy(propertyDescription = description, propertyDescriptionError = false)
    }

    fun omPropertyPriceChanged(price: String) {
        _uiState.value = _uiState.value.copy(propertyPrice = price, propertyPriceError = false)
    }

    fun onPhoneNumberChanged(phone: String) {
        _uiState.value = _uiState.value.copy(userPhoneNumber = phone, userPhoneNumberError = false)
    }

    fun uploadImage() {
        // TODO:  
    }


}