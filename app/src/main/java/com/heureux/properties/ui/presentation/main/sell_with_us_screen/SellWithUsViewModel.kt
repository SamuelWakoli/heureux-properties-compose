package com.heureux.properties.ui.presentation.main.sell_with_us_screen

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

    val userProfileData = profileRepository.getUserProfileData(onSuccess = {}, onFailure = {})
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )

    fun sendSellWithUsRequest(
        sellWithUsRequest: SellWithUsRequest,
        onSuccessListener: () -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = viewModelScope.launch {
        propertiesRepository.sendSellWithUsRequest(
            sellWithUsRequest = sellWithUsRequest,
            onSuccessListener = onSuccessListener,
            onFailure = onFailure
        )
    }



}