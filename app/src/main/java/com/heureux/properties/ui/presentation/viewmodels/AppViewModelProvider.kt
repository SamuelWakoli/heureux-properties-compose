package com.heureux.properties.ui.presentation.viewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.heureux.properties.HeureuxApp

object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            AuthViewModel(userAuthRepository = heureuxApp().container.userAuthRepository)
        }
        initializer {
            MainScreenViewModel(heureuxFirestoreRepository = heureuxApp().container.heureuxFirestoreRepository)
        }
    }
}

fun CreationExtras.heureuxApp(): HeureuxApp =
    (this[APPLICATION_KEY] as HeureuxApp)