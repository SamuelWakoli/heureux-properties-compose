package com.heureux.properties.ui.presentation.authgate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
//    viewModel: AuthViewModel,
    mainNavController: NavController,
) {


    val coroutineScope = rememberCoroutineScope()

    var name by rememberSaveable {
        mutableStateOf("")
    }
    var nameError by rememberSaveable {
        mutableStateOf(false)
    }

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

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Registration") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
            )
        }
    ) { scaffoldPaddingValues ->

        // TODO: Add some graphics here where necessary
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxHeight()
                .widthIn(min = 400.dp, max = 600.dp)
                .padding(horizontal = 16.dp)
                .padding(scaffoldPaddingValues)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { newName -> name = newName },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Name") },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = null)
                },
                supportingText = {
                    if (nameError) Text(text = "Name cannot be empty")
                },
                isError = nameError,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
            )
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
                        if (name.isEmpty()) {
                            nameError = true
                            coroutineScope.launch {
                                delay(5000L)
                                nameError = false
                            }
                        } else if (email.isEmpty()) {
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
                            // TODO: Register user with email and password
                        }
                    }
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
            )
            Spacer(modifier = Modifier.size(8.dp))

            OutlinedButton(onClick = {
                if (name.isEmpty()) {
                    nameError = true
                    coroutineScope.launch {
                        delay(5000L)
                        nameError = false
                    }
                } else if (email.isEmpty()) {
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
                    // TODO: Register user with email and password
                }
            }, modifier = Modifier.fillMaxWidth()) {
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
            ElevatedButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Row {
                    Text(text = "Sign in with Google")
                }
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
//                viewModel = viewModel(factory = AppViewModelProvider.Factory),
                mainNavController = mainNavController,
            )
        }
    }
}