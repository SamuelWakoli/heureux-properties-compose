package com.heureux.properties.ui.presentation.main.edit_profile_screen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.properties.data.FirebaseDirectories
import com.heureux.properties.data.repositories.ProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditProfileScreenUiState(
    val isLoadingUserData: Boolean = true,
    val userName: String = "",
    val userNameError: Boolean = false,
    val phoneNumber: String = "",
    val phoneNumberError: Boolean = false,
    val photoURL: String = "",
    val showBottomSheet: Boolean = false,
    val isSaving: Boolean = false,
)

class EditProfileScreenViewModel(val profileRepository: ProfileRepository) : ViewModel() {

    // by lazy so as to buy time for current user to be fetched / configured
    // after authentication
    val userProfileData by lazy {
        profileRepository.getUserProfileData(
            onSuccess = {},
            onFailure = {}
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = null
        )
    }

    private val _uiState: MutableStateFlow<EditProfileScreenUiState> by lazy {
        MutableStateFlow(
            EditProfileScreenUiState(
                userName = "",
                phoneNumber = "",
            )
        )
    }

    init {
        /** after 4 seconds (estimated time), the [userProfileData] should have been updated
         * so we get to update the [_uiState] with latest data
         */
        viewModelScope.launch {
            delay(4_000L)
        }.invokeOnCompletion {
            _uiState.update {
                it.copy(
                    userName = userProfileData.value?.displayName ?: "",
                    phoneNumber = userProfileData.value?.phone ?: "",
                    isLoadingUserData = false,
                )
            }
        }
    }

    val uiState: StateFlow<EditProfileScreenUiState> by lazy { _uiState.asStateFlow() }

    fun uploadProfileImage(
        uri: Uri,
        onSuccess: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = viewModelScope.launch {
        profileRepository.uploadImageGetUrl(
            uri = uri,
            directory = "${FirebaseDirectories.UsersStorageReference.name}/${userProfileData.value?.userEmail ?: Firebase.auth.currentUser?.email!!}/profile image",
            onSuccessListener = onSuccess,
            onFailure = onFailure,
        )
    }

    fun updatePhotoURL(photoURL: String) {
        _uiState.update { it.copy(photoURL = photoURL) }
    }

    fun updateUserName(userName: String) =
        _uiState.update { it.copy(userName = userName) }

    fun updatePhoneNumber(phoneNumber: String) =
        _uiState.update { it.copy(phoneNumber = phoneNumber) }

    fun hideOrShowEditProfileBottomSheet() =
        _uiState.update { it.copy(showBottomSheet = !it.showBottomSheet) }

    fun updateProfile(onSuccess: () -> Unit, onFailure: (exception: Exception) -> Unit) {
        if (uiState.value.userName.isEmpty()) {
            _uiState.update {
                it.copy(
                    userNameError = true
                )
            }
        } else if (uiState.value.phoneNumber.isEmpty()) {
            _uiState.update {
                it.copy(
                    phoneNumberError = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    userNameError = false,
                    phoneNumberError = false,
                    isSaving = true
                )
            }



            viewModelScope.launch {
                profileRepository.updateUserProfile(
                    userProfileData = userProfileData.value!!.copy(
                        displayName = uiState.value.userName,
                        phone = uiState.value.phoneNumber,
                    ),
                    onSuccess = {
                        onSuccess()
                    },
                    onFailure = { exception: Exception ->
                        onFailure(exception)
                        _uiState.update {
                            it.copy(
                                isSaving = false
                            )
                        }
                    }
                )
            }.invokeOnCompletion {
                _uiState.update {
                    it.copy(
                        isSaving = false
                    )
                }
            }
        }
    }
}
