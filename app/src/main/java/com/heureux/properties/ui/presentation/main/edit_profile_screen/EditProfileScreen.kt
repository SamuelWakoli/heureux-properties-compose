package com.heureux.properties.ui.presentation.main.edit_profile_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.heureux.properties.ui.presentation.composables.images.CoilImage
import com.heureux.properties.ui.presentation.viewmodels.AppViewModelProvider
import com.heureux.properties.ui.presentation.viewmodels.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    mainNavController: NavController,
    mainScreenViewModel: MainScreenViewModel,
) {

    val uiState = mainScreenViewModel.profileScreenUiState.collectAsState().value
    val userData = mainScreenViewModel.userData.collectAsState().value

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    var userName by rememberSaveable {
        mutableStateOf(userData?.name ?: "")
    }
    var userNameError by rememberSaveable {
        mutableStateOf(false)
    }

    var phoneNumber by rememberSaveable {
        mutableStateOf(userData?.phone ?: "")
    }
    var phoneNumberError by rememberSaveable {
        mutableStateOf(false)
    }

    var showBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }

    var isSaving by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(navigationIcon = {
            IconButton(onClick = { mainNavController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate back"
                )
            }
        }, title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Edit Profile")
            }
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary
        )
        )
    }) { paddingValues ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                    if (userData?.photoUrl != null && userData.photoUrl != "null") {
                        CoilImage(
                            modifier = Modifier.size(64.dp),
                            imageUrl = userData.photoUrl,
                            applyCircleShape = true,
                            errorContent = {
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = "Profile",
                                    modifier = Modifier.size(256.dp)
                                )
                            },
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Profile image",
                            modifier = Modifier.size(256.dp),
                        )
                    }
                TextButton(onClick = { /*TODO*/
                    showBottomSheet = true
                }) {
                    Text(text = "Select Image")
                }
            }


            Spacer(modifier = Modifier.size(16.dp))


            OutlinedTextField(
                value = userName,
                onValueChange = { newName ->
                    userName = newName
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(text = "Name")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = null,
                    )
                },
                supportingText = {
                    if (userNameError) Text(text = "Name cannot be empty")
                },
                isError = userNameError,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                singleLine = true,
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { newPhoneNumber ->
                    phoneNumber = newPhoneNumber
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(text = "Phone")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null,
                    )
                },
                supportingText = {
                    if (phoneNumberError) Text(text = "Phone cannot be empty")
                },
                isError = phoneNumberError,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done,
                ),
                singleLine = true,
            )


            Spacer(modifier = Modifier.size(8.dp))


            ElevatedButton(
                onClick = { /*TODO*/
                    isSaving = true
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                        Text(text = "Save")

                    if (isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }

        }




        if (showBottomSheet) ModalBottomSheet(onDismissRequest = { /*TODO*/
            showBottomSheet = false
        }) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = "Select image from", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.size(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Card(
                        onClick = {
                            // TODO:
                            showBottomSheet = false
                        }, shape = MaterialTheme.shapes.large, colors = CardDefaults.cardColors(
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                contentDescription = null,
                                modifier = Modifier.size(56.dp)
                            )
                            Text(text = "Gallery", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                    Spacer(modifier = Modifier.size(64.dp))
                    Card(
                        onClick = {
                            // TODO:
                            showBottomSheet = false
                        }, shape = MaterialTheme.shapes.large, colors = CardDefaults.cardColors(
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Camera,
                                contentDescription = null,
                                modifier = Modifier.size(56.dp)
                            )
                            Text(text = "Camera", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
                Spacer(modifier = Modifier.size(64.dp))
            }
        }
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    EditProfileScreen(
        mainNavController = rememberNavController(),
        mainScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    )
}