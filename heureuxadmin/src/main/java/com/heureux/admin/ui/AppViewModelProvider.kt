package com.heureux.admin.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.heureux.admin.HeureuxAdminApp
import com.heureux.admin.ui.presentation.authgate.AuthViewModel
import com.heureux.admin.ui.presentation.screens.add_property_screen.AddPropertyScreenViewModel
import com.heureux.admin.ui.presentation.screens.edit_profile_screen.EditProfileScreenViewModel
import com.heureux.admin.ui.presentation.screens.inquiries_screens.InquiriesViewModel
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreenViewModel
import com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.home_screen.HomeScreenViewModel
import com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.more_screen.MoreScreenViewModel
import com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.users_screen.UsersScreenViewModel
import com.heureux.admin.ui.presentation.screens.profile_screen.ProfileScreenViewModel

/**
 * Provides view models for the Heureux Admin app.
 */
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
        initializer {
            ProfileScreenViewModel(
                profileRepository = heureuxAdminApp().container.profileRepository
            )
        }
        initializer {
            EditProfileScreenViewModel(
                profileRepository = heureuxAdminApp().container.profileRepository
            )
        }
        initializer {
            MoreScreenViewModel(
                userPreferencesRepository = heureuxAdminApp().userPreferencesRepository,
                usersRepository = heureuxAdminApp().container.usersRepository,
                paymentsRepository = heureuxAdminApp().container.paymentsRepository,
                propertiesRepository = heureuxAdminApp().container.propertyRepository
            )
        }
        initializer {
            AddPropertyScreenViewModel(profileRepository = heureuxAdminApp().container.profileRepository)
        }

        initializer {
            HomeScreenViewModel(propertyRepository = heureuxAdminApp().container.propertyRepository)
        }
        initializer {
            UsersScreenViewModel(
                usersRepository = heureuxAdminApp().container.usersRepository,
                paymentsRepository = heureuxAdminApp().container.paymentsRepository,
                propertyRepository = heureuxAdminApp().container.propertyRepository,
            )
        }
        initializer {
            InquiriesViewModel(
                usersRepository = heureuxAdminApp().container.usersRepository,
                inquiriesRepository = heureuxAdminApp().container.inquiriesRepository,
                paymentsRepository = heureuxAdminApp().container.paymentsRepository,
                propertiesRepository = heureuxAdminApp().container.propertyRepository
            )
        }
    }
}

/**
 * Gets the Heureux Admin app instance from the creation extras.
 */
fun CreationExtras.heureuxAdminApp(): HeureuxAdminApp =
    (this[APPLICATION_KEY] as HeureuxAdminApp)