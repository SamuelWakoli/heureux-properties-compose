package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heureux.admin.data.repositories.PropertyRepository
import com.heureux.admin.data.types.HeureuxProperty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class HomeScreenUiState(
    val showDeleteDialog: Boolean = false,
    val currentProperty: HeureuxProperty? = null,
    val showErrorMessage: Boolean = false,
    val errorMessage: String? = null
)

class HomeScreenViewModel(private val propertyRepository: PropertyRepository) : ViewModel() {
    private var _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    companion object {
        private const val TAG = "HomeScreenViewModel"
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val propertiesList = propertyRepository.getAllProperties(onFailure = {}).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = null
    )

    fun resetUiState() {
        _uiState = MutableStateFlow(HomeScreenUiState())
    }


    fun onPropertyClicked(property: HeureuxProperty) {
        _uiState.value = _uiState.value.copy(currentProperty = property)
    }

    fun updateDeleteDialogState() {
        _uiState.update {
            it.copy(showDeleteDialog = !it.showDeleteDialog)
        }
    }

    fun updateCurrentProperty(property: HeureuxProperty) {
        _uiState.update {
            it.copy(currentProperty = property)
        }
    }

    fun onDeletePropertyClicked(property: HeureuxProperty) {
        _uiState.value = _uiState.value.copy(
            showDeleteDialog = true, currentProperty = property
        )

        viewModelScope.launch {
            propertyRepository.deleteProperty(id = property.id, onSuccess = {
                _uiState.update {
                    it.copy(showDeleteDialog = false)
                }
            }, onFailure = {
                _uiState.update {
                    it.copy(
                        showDeleteDialog = false,
                        showErrorMessage = true,
                        errorMessage = it.errorMessage,
                    )
                }
            })
        }
    }

    fun addProperty(
        property: HeureuxProperty,
        onSuccess: () -> Unit = {},
        onError: (exception: Exception) -> Unit = {}
    ) {
        viewModelScope.launch {
            propertyRepository.addProperty(
                data = property,
                onSuccess = {
                    _uiState.update {
                        it.copy(showErrorMessage = false)
                    }
                    onSuccess()
                }, onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            showDeleteDialog = false,
                            showErrorMessage = true,
                            errorMessage = exception.message,
                        )
                    }
                    onError(exception)
                })
        }
    }

    fun editProperty(
        property: HeureuxProperty,
        onSuccess: () -> Unit = {},
        onError: (exception: Exception) -> Unit = {}
    ) {
        viewModelScope.launch {
            propertyRepository.updateProperty(data = property, onSuccess = {
                _uiState.update {
                    it.copy(showErrorMessage = false)
                }
                onSuccess()
            }, onFailure = { exception ->
                _uiState.update {
                    it.copy(
                        showDeleteDialog = false,
                        showErrorMessage = true,
                        errorMessage = exception.message,
                    )
                }
                onError(exception)
            })
        }
    }
}