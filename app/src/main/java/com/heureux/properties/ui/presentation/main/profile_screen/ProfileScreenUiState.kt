package com.heureux.properties.ui.presentation.main.profile_screen

import com.google.firebase.auth.FirebaseUser

data class ProfileScreenUiState(
    val showSignOutDialog: Boolean = false,
    val showDeleteProfileDialog: Boolean = false,
    val currentUser: FirebaseUser? = null,
)
