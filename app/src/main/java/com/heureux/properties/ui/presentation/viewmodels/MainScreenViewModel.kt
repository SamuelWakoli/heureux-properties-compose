package com.heureux.properties.ui.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.properties.data.repositories.FirestoreRepository
import com.heureux.properties.data.types.HeureuxUser
import com.heureux.properties.ui.presentation.main.profile_screen.ProfileScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class MainScreenViewModel(private val heureuxFirestoreRepository: FirestoreRepository) :
    ViewModel() {

    private var _profileScreenUiState: MutableStateFlow<ProfileScreenUiState> =
        MutableStateFlow(ProfileScreenUiState())
    val profileScreenUiState: StateFlow<ProfileScreenUiState> = _profileScreenUiState.asStateFlow()

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val currentUser: FirebaseUser by lazy {
        Firebase.auth.currentUser!!
    }

    // by lazy so as to buy time for current user to be fetched / configured
    // after authentication
    val userData: StateFlow<HeureuxUser?> by lazy {
        heureuxFirestoreRepository.getHeureuxUserData(
            user = currentUser,
            onSuccess = {
                _profileScreenUiState.update {
                    it.copy(
                        currentUser = currentUser
                    )
                }
            },
            onFailure = { exception: Exception -> }
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )
    }


    suspend fun signOut() {
        Firebase.auth.signOut().wait()
        _profileScreenUiState.update {
            it.copy(
                currentUser = null
            )
        }
    }

    suspend fun deleteUserAndData() {
        heureuxFirestoreRepository.deleteUserAndData(
            userId = currentUser.email!!,
            onSuccess = {
                viewModelScope.launch {
                    signOut()
                }
            },
            onFailure = {},
        ).wait()
    }

    fun showOrHideSignOutDialog(value: Boolean) {
        _profileScreenUiState.update {
            it.copy(
                showSignOutDialog = value
            )
        }
    }

    fun showOrHideDeleteUserDataDialog(value: Boolean) {
        _profileScreenUiState.update {
            it.copy(
                showDeleteProfileDialog = value
            )
        }
    }
}