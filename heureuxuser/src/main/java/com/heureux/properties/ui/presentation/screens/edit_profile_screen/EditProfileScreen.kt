package com.heureux.properties.ui.presentation.screens.edit_profile_screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.heureux.properties.ui.AppViewModelProvider
import com.heureux.properties.ui.presentation.composables.images.CoilImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileScreenViewModel,
) {

    val uiState = viewModel.uiState.collectAsState().value
    val userData = viewModel.userProfileData.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    var profileImageUri: Uri? by remember { mutableStateOf(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                profileImageUri = uri
                Toast.makeText(
                    context, "Image uploading", Toast.LENGTH_LONG
                ).show()

                viewModel.uploadProfileImage(uri = uri, onSuccess = { photoURL ->
                    viewModel.updatePhotoURL(photoURL)
                }, onFailure = { exception ->
                    Toast.makeText(
                        context,
                        "An error occurred: [${exception.message}] Please try editing profile image later",
                        Toast.LENGTH_LONG
                    ).show()
                })
            }
        }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(scrollBehavior = scrollBehavior, navigationIcon = {
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
                    modifier = Modifier.padding(16.dp),
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CoilImage(
                                    modifier = Modifier.size(64.dp),
                                    imageUrl = userData.photoURL.toString(),
                                    applyCircleShape = true,
                                    errorContent = {
                                        Icon(
                                            imageVector = Icons.Outlined.AccountCircle,
                                            contentDescription = "Profile",
                                            modifier = Modifier.size(128.dp)
                                        )
                                    },
                                )


                                if (uiState.newPhotoURL.isNotEmpty()) {
                                    Spacer(modifier = Modifier.size(8.dp))
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = "to"
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                    CoilImage(
                                        modifier = Modifier.size(128.dp),
                                        imageUrl = uiState.newPhotoURL,
                                        applyCircleShape = true,
                                        errorContent = {
                                            Icon(
                                                imageVector = Icons.Outlined.AccountCircle,
                                                contentDescription = "New profile image",
                                                modifier = Modifier.size(128.dp)
                                            )
                                        },
                                    )
                                }


                            }
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = "Profile image",
                                modifier = Modifier.size(128.dp),
                            )
                        }
                        TextButton(onClick = {
                            launcher.launch("image/*")

                            /// The code comment below shows options for selecting image either from gallery
                            /// or from camera. Logically, most users prefer selecting image from gallery.
                            // viewModel.hideOrShowEditProfileBottomSheet()
                        }) {
                            Text(text = "Select Image")
                        }
                    }


                    OutlinedTextField(
                        value = uiState.userName,
                        onValueChange = { newName ->
                            viewModel.updateUserName(newName)
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
                            viewModel.updatePhoneNumber(newPhoneNumber)
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
                            viewModel.updateProfile(onSuccess = {
                                Toast.makeText(
                                    context, "Profile updated successfully", Toast.LENGTH_SHORT
                                ).show()
                                navController.navigateUp()
                            }, onFailure = { exception ->
                                Toast.makeText(
                                    context,
                                    "Internal error: ${exception.message}. Try again later",
                                    Toast.LENGTH_LONG
                                ).show()
                            })
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
            viewModel.hideOrShowEditProfileBottomSheet()
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
                            viewModel.hideOrShowEditProfileBottomSheet()
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
                            viewModel.hideOrShowEditProfileBottomSheet()
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
        viewModel = viewModel(factory = AppViewModelProvider.Factory),
    )
}