package com.heureux.properties.ui.presentation.authgate

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.presentation.composables.buttons.GoogleSignInButton
import com.heureux.properties.ui.presentation.navigation.Screens
import com.heureux.properties.ui.presentation.viewmodels.AppViewModelProvider
import com.heureux.properties.ui.presentation.viewmodels.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(
    viewModel: AuthViewModel,
    navController: NavController,
    onSignInWithGoogle: () -> Unit,
) {

    val uiState = viewModel.uiState.collectAsState().value

    val coroutineScope = viewModel.viewModelScope
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = uiState.isSignInSuccess, block = {
        if (uiState.isSignInSuccess) {
            Toast.makeText(
                context,
                "Profile created", Toast.LENGTH_LONG
            ).show()

            coroutineScope.launch {
                // Buy time for current user to initialise
                delay(2_000L)

            }.invokeOnCompletion {
                navController.navigate(Screens.MainScreen.route) {
                    launchSingleTop = true
                    navController.popBackStack()
                }
            }
        }
    })

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Registration") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
            )
        }
    ) { scaffoldPaddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(min = 400.dp, max = 600.dp)
                    .padding(horizontal = 16.dp)
                    .padding(scaffoldPaddingValues)
            ) {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = { newName -> viewModel.updateName(newName) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Name") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = null)
                    },
                    supportingText = {
                        if (uiState.showNameError) Text(text = "Name cannot be empty")
                    },
                    isError = uiState.showNameError,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                )
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { newEmail -> viewModel.updateEmail(newEmail) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Email") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    },
                    supportingText = {
                        if (uiState.showEmailError) Text(text = "Email cannot be empty")
                    },
                    isError = uiState.showEmailError,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                )
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { newPassword -> viewModel.updatePassword(newPassword) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Password") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Lock, contentDescription = null)
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.hideOrShowPassword() }) {
                            Icon(
                                imageVector =
                                if (uiState.showPassword) Icons.Outlined.VisibilityOff
                                else Icons.Outlined.Visibility,
                                contentDescription =
                                if (uiState.showPassword) "Hide password"
                                else "Show password"
                            )
                        }
                    },
                    supportingText = {
                        if (uiState.showPasswordError) Text(text = "Password cannot be empty")
                    },
                    isError = uiState.showPasswordError,
                    visualTransformation = if (uiState.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // first hide keyboard
                            keyboardController?.hide()
                            // then sign in
                            viewModel.registerWithEmailPwd()
                        }
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                )
                Spacer(modifier = Modifier.size(8.dp))
                if (uiState.errorMessage != null) Text(
                    text = uiState.errorMessage, color = MaterialTheme.colorScheme.error
                )

                OutlinedButton(
                    onClick = {
                        keyboardController?.hide()
                        viewModel.registerWithEmailPwd()
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Register",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(9f),
                            textAlign = TextAlign.Center,
                        )
                        if (uiState.isCreateAccountButtonLoading)
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 2.dp,
                            )
                        else
                            Icon(
                                imageVector = Icons.Default.AppRegistration,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(18.dp)
                                    .weight(1f)
                            )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                GoogleSignInButton(modifier = Modifier.fillMaxWidth()) {
                    onSignInWithGoogle()
                }
                Spacer(modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    MaterialTheme {
        Surface {
            val mainNavController = rememberNavController()
            RegistrationScreen(
                viewModel = viewModel(factory = AppViewModelProvider.Factory),
                navController = mainNavController,
            ) {}
        }
    }
}