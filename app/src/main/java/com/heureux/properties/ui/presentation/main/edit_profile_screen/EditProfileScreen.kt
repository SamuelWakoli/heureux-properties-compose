package com.heureux.properties.ui.presentation.main.edit_profile_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.presentation.composables.images.CoilImage
import com.heureux.properties.ui.presentation.viewmodels.AppViewModelProvider
import com.heureux.properties.ui.presentation.viewmodels.EditProfileScreenViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    editProfileScreenViewModel: EditProfileScreenViewModel,
) {

    val uiState = editProfileScreenViewModel.uiState.collectAsState().value
    val userData = editProfileScreenViewModel.userProfileData.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
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

        if (uiState.isLoadingUserData) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                    strokeCap = StrokeCap.Butt,

                    )
            }
            Spacer(modifier = Modifier.size(16.dp))
        } else {
            Column(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier.widthIn(max = 600.dp)
                ) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Column(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        if (userData?.photoURL != null && userData.photoURL.toString() != "null") {
                            CoilImage(
                                modifier = Modifier.size(64.dp),
                                imageUrl = userData.photoURL.toString(),
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
                            editProfileScreenViewModel.hideOrShowEditProfileBottomSheet()
                        }) {
                            Text(text = "Select Image")
                        }
                    }


                    OutlinedTextField(
                        value = uiState.userName,
                        onValueChange = { newName ->
                            editProfileScreenViewModel.updateUserName(newName)
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
                            if (uiState.userNameError) Text(text = "Name cannot be empty")
                        },
                        isError = uiState.userNameError,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        ),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium,
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    OutlinedTextField(
                        value = uiState.phoneNumber,
                        onValueChange = { newPhoneNumber ->
                            editProfileScreenViewModel.updatePhoneNumber(newPhoneNumber)
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
                            if (uiState.phoneNumberError) Text(text = "Phone cannot be empty")
                        },
                        isError = uiState.phoneNumberError,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done,
                        ),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium,
                    )


                    Spacer(modifier = Modifier.size(8.dp))


                    ElevatedButton(
                        onClick = {
                            keyboardController?.hide()
                            editProfileScreenViewModel.updateProfile(
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Profile updated successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigateUp()
                                },
                                onFailure = {
                                    Toast.makeText(
                                        context,
                                        "Failed to update profile",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Text(
                                text = "Save", style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(9f),
                                textAlign = TextAlign.Center,
                            )

                            if (uiState.isSaving) {
                                Spacer(modifier = Modifier.width(12.dp))
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.primary,
                                    strokeWidth = 2.dp,
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.DoneAll,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .weight(1f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }




        if (uiState.showBottomSheet) ModalBottomSheet(onDismissRequest = { /*TODO*/
            editProfileScreenViewModel.hideOrShowEditProfileBottomSheet()
        }) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = "Select Image", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.size(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Card(
                        onClick = {
                            // TODO:
                            editProfileScreenViewModel.hideOrShowEditProfileBottomSheet()
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
                            editProfileScreenViewModel.hideOrShowEditProfileBottomSheet()
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
        navController = rememberNavController(),
        editProfileScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    )
}