package com.heureux.admin.ui.presentation.screens.add_property_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.admin.ui.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPropertyScreen(
    navController: NavController,
    viewModel: AddPropertyScreenViewModel,
) {


    val userData = viewModel.userProfileData.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(userData) {
        if (userData != null) {
            viewModel.loadUserPhoneNumber()
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate back"
                    )
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Sell, contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Sell with us")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
        )
    }) { paddingValues ->
        if (userData == null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                    strokeWidth = 2.dp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        Modifier.widthIn(min = 400.dp, max = 600.dp)
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "The information you are about to provide below will be analysed by our agents and more inspections will be done.")
                        Spacer(modifier = Modifier.size(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            text = "Property details",
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        OutlinedTextField(
                            value = uiState.propertyName,
                            onValueChange = { name ->
                                viewModel.onPropertyNameChanged(name = name)
                            },
                            label = {
                                Text(text = "Name")
                            },
                            supportingText = {
                                if (uiState.propertyNameError) Text(text = "name cannot be empty")
                            },
                            isError = uiState.propertyNameError,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                            ),
                            singleLine = true,
                            shape = MaterialTheme.shapes.medium,
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        OutlinedTextField(
                            value = uiState.propertyDescription,
                            onValueChange = { description ->
                                viewModel.onPropertyDescriptionChanged(description)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Description")
                            },
                            supportingText = {
                                if (uiState.propertyDescriptionError) Text(text = "Description cannot be empty")
                            },
                            isError = false,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                            ),
                            minLines = 3,
                            maxLines = 6,
                            shape = MaterialTheme.shapes.medium,
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        OutlinedTextField(
                            value = uiState.propertyPrice,
                            onValueChange = { price ->
                                viewModel.omPropertyPriceChanged(price)
                            },
                            label = {
                                Text(text = "Price in Ksh.")
                            },
                            supportingText = {
                                if (uiState.propertyPriceError) Text(text = "Amount cannot be empty")
                            },
                            isError = false,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            ),
                            singleLine = true,
                            shape = MaterialTheme.shapes.medium,
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        OutlinedButton(
                            onClick = {
                                navController.navigate(Screens.AddPropertyImagesScreen.route) {
                                    launchSingleTop = true
                                }
                            }, modifier = Modifier.align(Alignment.End)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Upload images")
                                Spacer(modifier = Modifier.size(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Add, contentDescription = null
                                )
                            }
                        }
                        if (uiState.propertyImagesError) {
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = "Upload at least one image",
                                modifier = Modifier.align(Alignment.End),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            text = "Contact",
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        OutlinedTextField(
                            value = uiState.userPhoneNumber,
                            onValueChange = { phone ->
                                viewModel.onPhoneNumberChanged(phone)
                            },
                            label = {
                                Text(text = "Phone number")
                            },
                            supportingText = {
                                if (uiState.userPhoneNumberError) Text(text = "Phone number cannot be empty")
                            },
                            isError = false,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            ),
                            singleLine = true,
                            shape = MaterialTheme.shapes.medium,
                        )
                        Spacer(modifier = Modifier.padding(16.dp))
                    }
                }

                ElevatedButton(modifier = Modifier.padding(8.dp), onClick = {

                    //TODO

                //                    viewModel.sendSellWithUsRequest(
//                        sellWithUsRequest = SellWithUsRequest(
//                            time = LocalDateTime.now().toString(),
//                            userId = userData.userEmail ?: Firebase.auth.currentUser?.email ?: "anonymous",
//                            propertyName = uiState.propertyName,
//                            propertyDescription = uiState.propertyDescription,
//                            propertyPrice = uiState.propertyPrice,
//                            propertyImages = uiState.propertyImages,
//                            contactNumber = uiState.userPhoneNumber,
//                        ),
//                        onSuccessListener = {
//                            Toast.makeText(context, "Request sent", Toast.LENGTH_SHORT).show()
//                            navController.navigateUp()
//                        },
//                        onFailure = { exception: Exception ->
//                            Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
//                        },
//                    )
                }) {
                    Row(
                        modifier = Modifier.widthIn(min = 300.dp, max = 400.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Submit",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Icon(
                            imageVector = Icons.Default.DoneAll, contentDescription = null
                        )
                        if (uiState.uploadingRequest) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                strokeWidth = 2.dp,
                            )
                        } else {
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }
                }
            }
        }
    }
}