package com.heureux.properties.ui.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.heureux.properties.data.AuthRepository
import com.heureux.properties.ui.presentation.authgate.GoogleSignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SignInUiModel(
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

    val showDialogPwdResetEmailSent: Boolean = false
)

class AuthViewModel(userAuthRepository: AuthRepository) : ViewModel() {
    private var _uiState: MutableStateFlow<SignInUiModel> = MutableStateFlow(SignInUiModel())
    val uiState: StateFlow<SignInUiModel> = _uiState.asStateFlow()

    fun onSignInResult(result: GoogleSignInResult) {
        _uiState.update {
            it.copy(
                isSignInSuccess = result.data != null,
                errorMessage = result.errorMessage,
            )
        }
    }
}