package com.heureux.properties.ui.presentation.authgate

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.R
import com.heureux.properties.ui.presentation.composables.buttons.GoogleSignInButton
import com.heureux.properties.ui.presentation.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
//    viewModel: AuthViewModel,
    mainNavController: NavController,
    onSignInWithGoogle: () -> Unit,
) {


    val coroutineScope = rememberCoroutineScope()

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var emailError by rememberSaveable {
        mutableStateOf(false)
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }
    var passwordError by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }

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
                                mainNavController.navigate(route = Screens.RegistrationScreen.route) {
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
                    value = email,
                    onValueChange = { newEmail -> email = newEmail },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Email") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    },
                    supportingText = {
                        if (emailError) Text(text = "Email cannot be empty")
                    },
                    isError = emailError,
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { newPassword -> password = newPassword },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Password") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Lock, contentDescription = null)
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                imageVector =
                                if (passwordVisibility) Icons.Outlined.VisibilityOff
                                else Icons.Outlined.Visibility,
                                contentDescription =
                                if (passwordVisibility) "Hide password"
                                else "Show password"
                            )
                        }
                    },
                    supportingText = {
                        if (passwordError) Text(text = "Password cannot be empty")
                    },
                    isError = passwordError,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (email.isEmpty()) {
                                emailError = true
                                coroutineScope.launch {
                                    delay(5000L)
                                    emailError = false
                                }
                            } else if (password.isEmpty()) {
                                passwordError = true
                                coroutineScope.launch {
                                    delay(5000L)
                                    passwordError = false
                                }
                            } else {
                                // TODO: Sign in user with email and password
                            }
                        }
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                )
                Spacer(modifier = Modifier.size(8.dp))

                OutlinedButton(onClick = {
                    if (email.isEmpty()) {
                        emailError = true
                        coroutineScope.launch {
                            delay(5000L)
                            emailError = false
                        }
                    } else if (password.isEmpty()) {
                        passwordError = true
                        coroutineScope.launch {
                            delay(5000L)
                            passwordError = false
                        }
                    } else {
                        // TODO: Sign in user with email and password
                    }
                }, modifier = Modifier.fillMaxWidth()) {
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
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
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
private fun SignInScreenPreview() {
    MaterialTheme {
        Surface {
            val mainNavController = rememberNavController()
            SignInScreen(
//                viewModel = viewModel(factory = AppViewModelProvider.Factory),
                mainNavController = mainNavController,
            ) {}
        }
    }
}