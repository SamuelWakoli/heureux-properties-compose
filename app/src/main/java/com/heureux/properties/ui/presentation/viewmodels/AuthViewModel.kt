package com.heureux.properties.ui.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.properties.data.repositories.FirestoreRepository
import com.heureux.properties.ui.presentation.authgate.GoogleSignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

class AuthViewModel(heureuxFirestoreRepository: FirestoreRepository) : ViewModel() {
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

            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { authResultTask ->
                    if (authResultTask.isSuccessful) {
                        _uiState.update {
                            it.copy(
                                isSignInSuccess = true,
                            )
                        }
                    }
                    if (authResultTask.isCanceled) {
                        _uiState.update {
                            it.copy(
                                isSignInButtonLoading = false,
                                errorMessage = "Sign in canceled"
                            )
                        }
                    }
                }.addOnFailureListener { exception ->
                    _uiState.update {
                        it.copy(
                            isSignInButtonLoading = false,
                            errorMessage = exception.message
                        )
                    }
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

            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { authResultTask ->
                    if (authResultTask.isSuccessful) {
                        // since user registration is successful, update display name
                        Firebase.auth.currentUser?.updateProfile(
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                        )


                        _uiState.update {
                            it.copy(
                                isSignInSuccess = true,
                            )
                        }
                    }
                    if (authResultTask.isCanceled) {
                        _uiState.update {
                            it.copy(
                                isCreateAccountButtonLoading = false,
                                errorMessage = "Registration canceled",
                            )
                        }
                    }
                }.addOnFailureListener { exception ->
                    _uiState.update {
                        it.copy(
                            isCreateAccountButtonLoading = false,
                            errorMessage = exception.message
                        )
                    }
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

        Firebase.auth.sendPasswordResetEmail(email).addOnSuccessListener {
            _uiState.update {
                it.copy(
                    isSignInButtonLoading = !it.isSignInButtonLoading,
                    showDialogPwdResetEmailSent = !it.showDialogPwdResetEmailSent,
                )
            }
        }.addOnFailureListener { error ->
            _uiState.update {
                it.copy(
                    isSignInButtonLoading = false,
                    showDialogPwdResetEmailSent = !it.showDialogPwdResetEmailSent,
                    errorMessage = error.message
                )
            }
        }

    }

    fun hidePasswordResetDialog() {
        _uiState.update {
            it.copy(
                showDialogPwdResetEmailSent = false,
            )
        }
    }
}