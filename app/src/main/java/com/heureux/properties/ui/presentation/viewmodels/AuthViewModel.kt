package com.heureux.properties.ui.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heureux.properties.data.repositories.ProfileRepository
import com.heureux.properties.ui.presentation.authgate.GoogleSignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SignInUiState(
    // userData
    val name: String = "",
    val email: String = "",
    val password: String = "",

    // observed at launchedEffect
    val isSignInSuccess: Boolean = false,
    val errorMessage: String? = null,

    val isSignInButtonLoading: Boolean = false,
    val isCreateAccountButtonLoading: Boolean = false,
    val showPassword: Boolean = false,
    val showNameError: Boolean = false,
    val showEmailError: Boolean = false,
    val showPasswordError: Boolean = false,

    val showDialogPwdResetEmailSent: Boolean = false,
)

class AuthViewModel(val profileRepository: ProfileRepository) : ViewModel() {
    private var _uiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onSignInResult(result: GoogleSignInResult) {
        _uiState.update {
            it.copy(
                isSignInSuccess = result.data != null,
                errorMessage = result.errorMessage,
            )
        }
    }

    fun updateName(value: String) {
        _uiState.update {
            it.copy(
                name = value,
                showNameError = false,
                errorMessage = null,
            )
        }
    }


    fun updateEmail(value: String) {
        _uiState.update {
            it.copy(
                email = value,
                showEmailError = false,
                errorMessage = null,
            )
        }
    }

    fun updatePassword(value: String) {

        _uiState.update {
            it.copy(
                password = value,
                showPasswordError = false,
                errorMessage = null
            )
        }
    }

    fun hideOrShowPassword() {
        _uiState.update { it.copy(showPassword = !(it.showPassword)) }
    }

    fun hidePasswordResetDialog() {
        _uiState.update {
            it.copy(
                showDialogPwdResetEmailSent = false,
            )
        }
    }

    fun signInWithEmailPwd() {
        val email = uiState.value.email
        val password = uiState.value.password
        if (email.isEmpty()) {
            _uiState.update { it.copy(showEmailError = true) }
        } else if (password.isEmpty()) {
            _uiState.update { it.copy(showPasswordError = true) }
        } else {
            _uiState.update {
                it.copy(
                    isSignInButtonLoading = true
                )
            }

            viewModelScope.launch {
                profileRepository.signIn(
                    email = email,
                    password = password,
                    onSuccessListener = {
                        _uiState.update {
                            it.copy(
                                isSignInSuccess = true,
                            )
                        }
                    },
                    onErrorListener =  {exception ->
                        _uiState.update {
                            it.copy(
                                isSignInButtonLoading = false,
                                errorMessage = exception.message
                            )
                        }
                    }
                )
            }
        }
    }

    fun registerWithEmailPwd() {
        val name = uiState.value.name
        val email = uiState.value.email
        val password = uiState.value.password

        if (name.isEmpty()) {
            _uiState.update { it.copy(showNameError = true) }
        } else if (email.isEmpty()) {
            _uiState.update { it.copy(showEmailError = true) }
        } else if (password.isEmpty()) {
            _uiState.update { it.copy(showPasswordError = true) }
        } else {
            _uiState.update {
                it.copy(
                    isCreateAccountButtonLoading = true
                )
            }

            viewModelScope.launch {
                profileRepository.registerUser(
                    name = name,
                    email = email,
                    password = password,
                    onSuccessListener = {
                        _uiState.update {
                            it.copy(
                                isSignInSuccess = true,
                            )
                        }
                    },
                    onErrorListener = {exception ->
                        _uiState.update {
                            it.copy(
                                isCreateAccountButtonLoading = false,
                                errorMessage = exception.message
                            )
                        }
                    }
                )
            }

        }
    }

    fun sendPasswordResetEmail() {
        val email = uiState.value.email
        if (email.isEmpty()) {
            _uiState.update { it.copy(showEmailError = true) }
        } else {
            _uiState.update {
                it.copy(
                    isSignInButtonLoading = true
                )
            }
        }

        viewModelScope.launch {
            profileRepository.sendPasswordResetEmail(
                email = email,
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isSignInButtonLoading = !it.isSignInButtonLoading,
                            showDialogPwdResetEmailSent = !it.showDialogPwdResetEmailSent,
                        )
                    }
                },
                onFailure = {exception: Exception ->
                    _uiState.update {
                        it.copy(
                            isSignInButtonLoading = false,
                            showDialogPwdResetEmailSent = !it.showDialogPwdResetEmailSent,
                            errorMessage = exception.message
                        )
                    }
                }

            )
        }
    }
}