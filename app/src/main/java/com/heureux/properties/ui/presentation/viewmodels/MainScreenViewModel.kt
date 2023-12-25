package com.heureux.properties.ui.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.properties.data.FirestoreRepository
import com.heureux.properties.data.HeureuxUser
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainScreenViewModel(heureuxFirestoreRepository: FirestoreRepository) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val currentUser = Firebase.auth.currentUser!!

    val userData: StateFlow<HeureuxUser?> = heureuxFirestoreRepository.getHeureuxUserData(
        user = currentUser,
        onSuccess = {

        },
        onFailure = { exception: Exception -> }
    ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )



}