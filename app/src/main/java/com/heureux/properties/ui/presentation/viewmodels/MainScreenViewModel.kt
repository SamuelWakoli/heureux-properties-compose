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

data class HomeScreenUiState(
    val currentUser: FirebaseUser? = null,
)

class MainScreenViewModel(private val profileRepository: ProfileRepository) :
    ViewModel() {

    // by lazy so as to buy time for current user to be fetched / configured
    // after authentication
    private val currentUser: FirebaseUser? by lazy {
        Firebase.auth.currentUser
    }


    private val _homeScreenUiState by lazy {
        MutableStateFlow(
            HomeScreenUiState(
                currentUser = currentUser
            )
        )
    }

    val homeScreenUiState: StateFlow<HomeScreenUiState> = _homeScreenUiState.asStateFlow()

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    // by lazy so as to buy time for current user to be fetched / configured
    // after authentication

    val userProfileData by lazy {
        // by lazy {} buys time for the current user to initialize after authentication or app startup
        profileRepository.getUserProfileData(
            onSuccess = {},
            onFailure = {}
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = null
        )
    }

}