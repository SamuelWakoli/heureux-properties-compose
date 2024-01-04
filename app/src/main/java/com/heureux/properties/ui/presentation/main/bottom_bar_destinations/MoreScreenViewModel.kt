package com.heureux.properties.ui.presentation.main.bottom_bar_destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heureux.properties.data.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MoreScreenUiState(
    val showThemeDialog: Boolean = false,
    val showDynamicThemeBottomSheet: Boolean = false,
)

class MoreScreenViewModel(
    val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    private var _uiState: MutableStateFlow<MoreScreenUiState> =
        MutableStateFlow(MoreScreenUiState())
    val uiState: StateFlow<MoreScreenUiState> = _uiState.asStateFlow()

    val currentThemeData = userPreferencesRepository.getThemeData
    val currentDynamicColorState = userPreferencesRepository.getDynamicColor


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