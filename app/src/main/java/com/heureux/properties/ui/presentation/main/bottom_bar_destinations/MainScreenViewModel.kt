package com.heureux.properties.ui.presentation.main.bottom_bar_destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.properties.data.repositories.ProfileRepository
import com.heureux.properties.data.repositories.PropertiesRepository
import com.heureux.properties.data.types.HeureuxProperty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class MainScreenUiState(
    val currentProperty: HeureuxProperty? = null,
)

class MainScreenViewModel(
    private val profileRepository: ProfileRepository,
    private val propertiesRepository: PropertiesRepository,
) :
    ViewModel() {

    private var _mainScreenUiState: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState())
    val mainScreenUiState: StateFlow<MainScreenUiState> = _mainScreenUiState.asStateFlow()

    // by lazy so as to buy time for current user to be fetched / configured
    // after authentication
    private val currentUser: FirebaseUser? by lazy {
        Firebase.auth.currentUser
    }

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

    val propertiesList: StateFlow<List<HeureuxProperty>?> =
        propertiesRepository.getHomeProperties { }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )

    val bookmarksList =
        propertiesRepository.getBookmarks(
            email = userProfileData.value?.userEmail ?: currentUser?.email ?: "",
        ) { }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )

    val userListings = propertiesRepository.getMyListings(
        email = userProfileData.value?.userEmail ?: currentUser?.email ?: ""
    ) {}.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = null
    )

    fun updateCurrentProperty(property: HeureuxProperty?) {
        _mainScreenUiState.update { it.copy(currentProperty = property) }
    }
}