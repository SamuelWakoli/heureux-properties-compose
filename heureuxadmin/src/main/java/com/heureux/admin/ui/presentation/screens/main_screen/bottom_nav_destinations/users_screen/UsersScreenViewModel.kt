package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.users_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heureux.admin.data.repositories.PaymentsRepository
import com.heureux.admin.data.repositories.UsersRepository
import com.heureux.admin.data.types.HeureuxUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

data class UsersScreenState(
    val currentUser: HeureuxUser? = null
)

class UsersScreenViewModel(
    private val usersRepository: UsersRepository,
    private val paymentsRepository: PaymentsRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(UsersScreenState())
    val uiState = _uiState.asStateFlow()

    companion object {
        const val TAG = "UsersScreenViewModel"
        const val TIMEOUT_DURATION = 5_000L
    }

    val allUsers = usersRepository.getAllUsers(
        onFailure = {

        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DURATION),
        initialValue = null
    )

    val allPayments = paymentsRepository.getAllPayments(onError = {}).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DURATION),
        initialValue = null
    )

    fun updateCurrentUser(user: HeureuxUser) {
        _uiState.value = _uiState.value.copy(currentUser = user)
    }

}