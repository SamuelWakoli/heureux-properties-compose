package com.heureux.properties.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.heureux.properties.HeureuxApp
import com.heureux.properties.ui.presentation.authgate.AuthViewModel
import com.heureux.properties.ui.presentation.main.edit_profile_screen.EditProfileScreenViewModel
import com.heureux.properties.ui.presentation.main.main_screen.MainScreenViewModel
import com.heureux.properties.ui.presentation.main.main_screen.bottom_bar_destinations.MoreScreenViewModel
import com.heureux.properties.ui.presentation.main.profile_screen.ProfileScreenViewModel
import com.heureux.properties.ui.presentation.main.sell_with_us_screen.SellWithUsViewModel

object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            AuthViewModel(profileRepository = heureuxApp().container.profileRepository)
        }
        initializer {
            MainScreenViewModel(
                profileRepository = heureuxApp().container.profileRepository,
                propertiesRepository = heureuxApp().container.propertiesRepository

            )
        }
        initializer {
            ProfileScreenViewModel(
                profileRepository = heureuxApp().container.profileRepository,
                propertiesRepository = heureuxApp().container.propertiesRepository
            )
        }
        initializer {
            EditProfileScreenViewModel(profileRepository = heureuxApp().container.profileRepository)
        }
        initializer {
            MoreScreenViewModel(userPreferencesRepository = heureuxApp().userPreferencesRepository)
        }
        initializer {
            SellWithUsViewModel(
                profileRepository = heureuxApp().container.profileRepository,
                propertiesRepository = heureuxApp().container.propertiesRepository
            )
        }
    }
}

fun CreationExtras.heureuxApp(): HeureuxApp =
    (this[APPLICATION_KEY] as HeureuxApp)