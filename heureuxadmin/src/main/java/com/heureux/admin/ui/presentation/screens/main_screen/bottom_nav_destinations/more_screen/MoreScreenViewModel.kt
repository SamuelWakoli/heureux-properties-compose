package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.more_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heureux.admin.data.repositories.PaymentsRepository
import com.heureux.admin.data.repositories.PropertyRepository
import com.heureux.admin.data.repositories.UserPreferencesRepository
import com.heureux.admin.data.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MoreScreenUiState(
    val showThemeDialog: Boolean = false,
    val showDynamicThemeBottomSheet: Boolean = false,
)

class MoreScreenViewModel(
    val userPreferencesRepository: UserPreferencesRepository,
    val usersRepository: UsersRepository,
    val paymentsRepository: PaymentsRepository,
    val propertiesRepository: PropertyRepository,
) : ViewModel() {

    companion object {
        const val TAG = "MoreScreenViewModel"
        const val TIMEOUT_DURATION = 5_000L
    }

    private var _uiState: MutableStateFlow<MoreScreenUiState> =
        MutableStateFlow(MoreScreenUiState())
    val uiState: StateFlow<MoreScreenUiState> = _uiState.asStateFlow()

    val currentThemeData = userPreferencesRepository.getThemeData
    val currentDynamicColorState = userPreferencesRepository.getDynamicColor

    val allPayments = paymentsRepository.getAllPayments { }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DURATION),
        initialValue = null
    )

    val allUsers = usersRepository.getAllUsers { }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DURATION),
        initialValue = null
    )

    val allProperties = propertiesRepository.getAllProperties { }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DURATION),
        initialValue = null
    )

    fun hideOrShowThemeDialog() {
        _uiState.update {
            it.copy(showThemeDialog = !it.showThemeDialog)
        }
    }

    fun hideOrShowDynamicThemeBottomSheet() {
        _uiState.update {
            it.copy(showDynamicThemeBottomSheet = !it.showDynamicThemeBottomSheet)
        }
    }

    fun updateThemeData(themeData: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveTheme(themeData)
        }
    }

    fun updateDynamicTheme(boolean: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveDynamicColor(boolean)
        }
    }
}