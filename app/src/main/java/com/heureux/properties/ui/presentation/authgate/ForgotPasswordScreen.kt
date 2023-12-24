package com.heureux.properties.ui.presentation.authgate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.presentation.viewmodels.AppViewModelProvider
import com.heureux.properties.ui.presentation.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    mainNavController: NavController,
    viewModel: AuthViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Forgot password") },
                navigationIcon = {
                    IconButton(onClick = { mainNavController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
            )
        },
    ) { padding ->

        Column (
            Modifier
                .padding(
                    padding
                )
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .widthIn(max = 600.dp),
            ) {
                Text(
                    text = "Enter your email to get password reset link",
                    modifier = Modifier.padding(8.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true,
                    isError = uiState.showEmailError,
                    supportingText = { if (uiState.showEmailError) Text(text = "Email cannot be empty") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email, contentDescription = null
                        )
                    },
                    label = { Text(text = "Email") },
                    trailingIcon = {
                        if (uiState.isSignInButtonLoading) CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp),
                        )
                        else IconButton(onClick = { viewModel.sendPasswordResetEmail() }) {
                            Icon(
                                imageVector = Icons.Outlined.Send,
                                contentDescription = "Send"
                            )
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions().copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email,
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.None,
                    ),
                )
                if (!uiState.errorMessage.isNullOrEmpty()) Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp),
                )

                if (uiState.showDialogPwdResetEmailSent) {
                    // TODO:
                }
            }
        }
    }
}


@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(
        mainNavController = rememberNavController(),
        viewModel = viewModel<AuthViewModel>(factory = AppViewModelProvider.Factory),
    )
}