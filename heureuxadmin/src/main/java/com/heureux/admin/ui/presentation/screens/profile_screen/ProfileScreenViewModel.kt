package com.heureux.admin.ui.presentation.screens.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.admin.data.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileScreenUiState(
    val showSignOutDialog: Boolean = false,
    val errorMessage: String? = null,
)

class ProfileScreenViewModel(
    val profileRepository: ProfileRepository,
) : ViewModel() {

    // by lazy so as to buy time for current user to be fetched / configured
    // after authentication
    private val currentUser: FirebaseUser? by lazy {
        Firebase.auth.currentUser
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private var _uiState: MutableStateFlow<ProfileScreenUiState> = MutableStateFlow(
        ProfileScreenUiState()
    )
    val uiState: StateFlow<ProfileScreenUiState> = _uiState.asStateFlow()

    val userProfileData by lazy {
        profileRepository.getUserProfileData(
            onSuccess = {

            },
            onFailure = { exception ->
                _uiState.update { it.copy(errorMessage = exception.message) }
            }
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )
    }

    val adminsList by lazy {
        profileRepository.getAdminsList(
            onFailure = {exception ->
                _uiState.update { it.copy(errorMessage = exception.message) }
            }
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )
    }

    fun resetErrorMessage() =
        _uiState.update { it.copy(errorMessage = null) }

    fun hideOrShowSignOutDialog() =
        _uiState.update { it.copy(showSignOutDialog = !it.showSignOutDialog) }

    fun signOut(onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            profileRepository.signOut()
        }.invokeOnCompletion {
            if (currentUser == null) {
                onSuccess()
            } else {
                onFailure()
            }
        }
    }
}
