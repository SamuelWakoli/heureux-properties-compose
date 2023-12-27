package com.heureux.properties.ui.presentation.main.edit_profile_screen

data class EditProfileScreenUiState(
    val userName: String = "",
    val userNameError: Boolean = false,
    val phoneNumber: String = "",
    val phoneNumberError: Boolean = false,
    val showBottomSheet: Boolean = false,
    val isSaving: Boolean = false,
)
