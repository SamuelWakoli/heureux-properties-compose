package com.heureux.properties.ui.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.properties.data.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileScreenUiState(
    val showSignOutDialog: Boolean = false,
    val showDeleteProfileDialog: Boolean = false,
)

class ProfileScreenViewModel(val profileRepository: ProfileRepository) : ViewModel() {

    // by lazy so as to buy time for current user to be fetched / configured
    // after authentication
    private val currentUser: FirebaseUser? by lazy {
        Firebase.auth.currentUser
    }

    private var _uiState: MutableStateFlow<ProfileScreenUiState> = MutableStateFlow(ProfileScreenUiState())
    val uiState: StateFlow<ProfileScreenUiState> = _uiState.asStateFlow()

    val userProfileData by lazy {
        profileRepository.getUserProfileData(
            onSuccess = {},
            onFailure = {}
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = null
        )
    }

    fun hideOrShowSignOutDialog() =
        _uiState.update { it.copy(showSignOutDialog = !it.showSignOutDialog) }

    fun hideOrShowDeleteProfileDialog() =
        _uiState.update { it.copy(showDeleteProfileDialog = !it.showDeleteProfileDialog) }

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

    fun deleteProfileAndData(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        viewModelScope.launch {
            profileRepository.deleteUserAndData(
                email = currentUser?.email!!,
                onSuccessListener = {
                    onSuccess()
                },
                onErrorListener = { exception: Exception ->
                    onFailure(exception)
                }
            )
        }
    }
}