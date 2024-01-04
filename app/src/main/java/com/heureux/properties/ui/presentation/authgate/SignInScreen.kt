package com.heureux.properties.ui.presentation.authgate

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.R
import com.heureux.properties.ui.presentation.composables.buttons.GoogleSignInButton
import com.heureux.properties.ui.presentation.navigation.Screens
import com.heureux.properties.ui.AppViewModelProvider

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    viewModel: AuthViewModel,
    navController: NavController,
    onSignInWithGoogle: () -> Unit,
) {

    val uiState = viewModel.uiState.collectAsState().value

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = uiState.isSignInSuccess, block = {
        if (uiState.isSignInSuccess) {
            Toast.makeText(
                context,
                "Signed in successfully", Toast.LENGTH_LONG
            ).show()

            navController.navigate(Screens.MainScreen.route) {
                launchSingleTop = true
                navController.popBackStack()
            }
        }
    })


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.heureux_background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.1f,
        )
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxHeight()
                .widthIn(min = 400.dp, max = 600.dp)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.size(24.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_round),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
                Text(
                    text = "Heureux\nProperties",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = "Bringing a smile in every investor",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Spacer(modifier = Modifier.size(64.dp))
            Column {
                Text(
                    text = "Sign In",
                    style = MaterialTheme.typography.titleLarge,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Don't have an account?")
                    Text(
                        text = "Register",
                        modifier = Modifier
                            .clickable {
                                navController.navigate(route = Screens.RegistrationScreen.route) {
                                    launchSingleTop = true
                                }
                            }
                            .padding(6.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
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
                            viewModel.signInWithEmailPwd()
                        }
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                )
                Text(
                    text = "Forgot password?",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(route = Screens.ForgotPasswordScreen.route) {
                                launchSingleTop = true
                            }
                        }
                        .padding(8.dp)
                        .align(Alignment.End),

                    )
                if (uiState.errorMessage != null) Text(
                    text = uiState.errorMessage, color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                OutlinedButton(
                    onClick = {
                        viewModel.signInWithEmailPwd()
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Sign in",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(9f),
                            textAlign = TextAlign.Center,
                        )
                        if (uiState.isSignInButtonLoading) {
                            Spacer(modifier = Modifier.width(12.dp))
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 2.dp,
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(18.dp)
                                    .weight(1f)
                            )
                        }
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
private fun SignInScreenPreview() {
    MaterialTheme {
        Surface {
            val mainNavController = rememberNavController()
            SignInScreen(
                viewModel = viewModel(factory = AppViewModelProvider.Factory),
                navController = mainNavController,
            ) {}
        }
    }
}