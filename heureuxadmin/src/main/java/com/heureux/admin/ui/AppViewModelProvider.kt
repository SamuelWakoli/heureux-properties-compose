package com.heureux.admin.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.heureux.admin.HeureuxAdminApp
import com.heureux.admin.ui.presentation.authgate.AuthViewModel
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreenViewModel

object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            AuthViewModel(profileRepository = heureuxAdminApp().container.profileRepository)
        }
        initializer {
            MainScreenViewModel(
                profileRepository = heureuxAdminApp().container.profileRepository,

            )
        }
    }
}

fun CreationExtras.heureuxAdminApp(): HeureuxAdminApp =
    (this[APPLICATION_KEY] as HeureuxAdminApp)