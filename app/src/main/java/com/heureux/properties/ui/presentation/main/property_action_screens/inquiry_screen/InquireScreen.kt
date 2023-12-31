package com.heureux.properties.ui.presentation.main.property_action_screens.inquiry_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Cases
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun InquiryScreen(
    navController: NavController,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(scrollBehavior = scrollBehavior, navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate back"
                )
            }
        }, title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cases, contentDescription = null
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "Inquiry")
            }
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary
        )
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
            Column(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .widthIn(min = 400.dp, max = 600.dp),
            ) {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Price: Ksh. 2,000,000",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Property Name",
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
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(text = "Ksh.")
                    },
                    supportingText = /*use null if no error*/ {
                        Text(text = "Amount cannot be empty")
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
                Divider()
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Select payment method",
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.padding(4.dp))
                PaymentMethodItem(label = "Full payment", currentValue = "Full payment") {

                }
                PaymentMethodItem(label = "Hire purchase", currentValue = "Full payment") {

                }
                PaymentMethodItem(label = "Bank finance", currentValue = "Full payment") {

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
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(text = "Phone number")
                    },
                    supportingText = /*use null if no error*/ {
                        Text(text = "Phone number cannot be empty")
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
            ElevatedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(4.dp)
                    .widthIn(min = 400.dp, max = 600.dp)
            ) {
                Text(text = "Submit", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Preview
@Composable
private fun InquiryScreenPreview() {
    InquiryScreen(
        navController = rememberNavController()
    )
}

@Composable
fun PaymentMethodItem(
    label: String,
    currentValue: String,
    onClick: (value: String) -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable {
            onClick(label)
        },
        leadingContent = {
            RadioButton(selected = label == currentValue, onClick = { onClick(label) })
        },
        headlineContent = {
            Text(text = label)
        },
    )
}

@Preview
@Composable
private fun PaymentMethodItemPreview() {
    PaymentMethodItem(
        label = "Full payment",
        currentValue = "Full payment",
        onClick = {},
    )
}