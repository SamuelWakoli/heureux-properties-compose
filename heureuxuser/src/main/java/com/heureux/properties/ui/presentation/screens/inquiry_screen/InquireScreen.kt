package com.heureux.properties.ui.presentation.screens.inquiry_screen

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Cases
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.data.types.InquiryItem
import com.heureux.properties.ui.AppViewModelProvider
import com.heureux.properties.ui.presentation.composables.buttons.RadioButtonListItem
import com.heureux.properties.ui.presentation.screens.main_screen.MainScreenViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun InquiryScreen(
    navController: NavController,
    viewModel: MainScreenViewModel,
) {

    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.mainScreenUiState.collectAsState().value
    val userProfileData = viewModel.userProfileData.collectAsState().value


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
                        imageVector = Icons.Outlined.Cases, contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Inquiry")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
        )
    }) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            if (userProfileData == null) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                    strokeWidth = 2.dp
                )
            } else {

                var offerAmount by rememberSaveable {
                    mutableStateOf("")
                }
                var offerAmountError by rememberSaveable {
                    mutableStateOf(false)
                }

                var paymentMethod by rememberSaveable {
                    mutableStateOf("Full payment")
                }

                var phoneNumber by rememberSaveable {
                    mutableStateOf(userProfileData.phone ?: "")
                }
                var phoneNumberError by rememberSaveable {
                    mutableStateOf(false)
                }

                var errorMessage by rememberSaveable {
                    mutableStateOf("")
                }

                Column(
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        Modifier.widthIn(min = 400.dp, max = 600.dp),
                    ) {
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            text = "Price: Ksh. ${uiState.currentProperty?.price}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = uiState.currentProperty?.name ?: "",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            text = "Make an offer",
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        OutlinedTextField(
                            value = offerAmount,
                            onValueChange = { value ->
                                offerAmount = value
                                offerAmountError = false
                            },
                            label = {
                                Text(text = "Ksh.")
                            },
                            supportingText = {
                                if (offerAmountError)
                                    Text(text = "Amount cannot be empty")
                            },
                            isError = offerAmountError,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    if (offerAmount.isEmpty()) offerAmountError = true
                                }
                            ),
                            singleLine = true,
                            shape = MaterialTheme.shapes.medium,
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Divider()
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            text = "Select payment method",
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        RadioButtonListItem(
                            label = "Full payment", currentValue = paymentMethod
                        ) { value ->
                            paymentMethod = value
                        }
                        RadioButtonListItem(
                            label = "Mortgage",
                            currentValue = paymentMethod
                        ) { value ->
                            paymentMethod = value
                        }
                        RadioButtonListItem(
                            label = "Bank finance", currentValue = paymentMethod
                        ) { value ->
                            paymentMethod = value
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
                            value = phoneNumber,
                            onValueChange = { value ->
                                phoneNumber = value
                                phoneNumberError = false
                            },
                            label = {
                                Text(text = "Phone number")
                            },
                            supportingText = {
                                if (phoneNumberError)
                                    Text(text = "Phone number cannot be empty")
                            },
                            isError = phoneNumberError,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (phoneNumber.isEmpty()) phoneNumberError = true
                                }
                            ),
                            singleLine = true,
                            shape = MaterialTheme.shapes.medium,
                        )
                        Spacer(modifier = Modifier.padding(16.dp))
                        if (errorMessage.isNotEmpty()) Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    ElevatedButton(
                        onClick = {
                            val time = LocalDateTime.now()

                            if (offerAmount.isEmpty()) {
                                offerAmountError = true
                            } else if (phoneNumber.isEmpty()) {
                                phoneNumberError = true
                            } else {
                                viewModel.submitInquiry(
                                    inquiryItem = InquiryItem(
                                        id = time.toString(),
                                        time = time.toString(),
                                        propertyId = uiState.currentProperty?.id ?: "",
                                        senderId = userProfileData.userEmail ?: "",
                                        offerAmount = offerAmount,
                                        preferredPaymentMethod = paymentMethod,
                                        phoneNumber = phoneNumber,
                                    ),
                                    onSuccess = {
                                        Toast.makeText(
                                            context,
                                            "Inquiry Submitted",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        navController.navigateUp()
                                    },
                                    onFailure = { exception: Exception ->
                                        errorMessage = "An error occurred: ${exception.message}"
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .widthIn(min = 400.dp, max = 600.dp)
                    ) {
                        Text(text = "Submit", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun InquiryScreenPreview() {
    InquiryScreen(
        navController = rememberNavController(),
        viewModel = viewModel(factory = AppViewModelProvider.Factory)
    )
}