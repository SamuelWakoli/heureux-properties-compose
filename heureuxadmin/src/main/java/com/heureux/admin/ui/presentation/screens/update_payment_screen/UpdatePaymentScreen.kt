package com.heureux.admin.ui.presentation.screens.update_payment_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.users_screen.UsersScreenViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePaymentScreen(
    navController: NavHostController,
    viewModel: UsersScreenViewModel,
) {
    val uiState = viewModel.uiState.collectAsState().value
    val currentUser = uiState.currentUser
    val allPayments = viewModel.allPayments.collectAsState().value
    val userPayments = allPayments?.filter { it.userId == currentUser?.email }
    val currentAdminName = Firebase.auth.currentUser?.displayName
    val context = navController.context

    val allProperties = viewModel.allProperties.collectAsState().value


    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Outlined.Payments, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Update payment")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.widthIn(max = 600.dp)
            ) {
                ListItem(
                    headlineContent = {
                        currentUser?.name?.let { Text(text = it) }
                    },
                    supportingContent = {
                        currentUser?.email?.let { Text(text = it) }
                    },
                    colors = ListItemDefaults.colors(
                        headlineColor = MaterialTheme.colorScheme.primary,
                        supportingColor = MaterialTheme.colorScheme.primary
                    ),
                )
                Spacer(modifier = Modifier.size(4.dp))
                Divider()
                Spacer(modifier = Modifier.size(4.dp))
                if (userPayments == null) {
                    CircularProgressIndicator()
                } else if (userPayments.isEmpty()) {
                    Text(
                        text = "No payments found",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    userPayments.forEach { paymentItem ->
                        if (allProperties != null) {
                            val property = allProperties.find { it.id == paymentItem.propertyId }
                            if (property != null) UpdatePaymentListItem(property = property,
                                paymentItem = paymentItem,
                                onClickAddPayment = { amount: String ->
                                    viewModel.addPayment(payment = paymentItem.copy(
                                        paymentId = LocalDateTime.now().toString(),
                                        amount = amount.replace(",", ""),
                                        totalAmountPaid = (paymentItem.totalAmountPaid.replace(
                                            ",", ""
                                        ).toInt() + amount.replace(
                                            ",", ""
                                        ).toInt()).toString(),
                                        time = LocalDateTime.now().toString(),
                                        owingAmount = (paymentItem.owingAmount.replace(
                                            ",", ""
                                        ).toInt() - amount.replace(
                                            ",", ""
                                        ).toInt()).toString(),
                                        approvedBy = currentAdminName.toString()
                                    ), onSuccess = {
                                        Toast.makeText(
                                            context,
                                            "Payment added successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }, onError = { exception: Exception ->
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = exception.message.toString(),
                                                withDismissAction = true,
                                                duration = SnackbarDuration.Indefinite
                                            )
                                        }
                                    })
                                },
                                onClickUndoPayment = {
                                    viewModel.unDoLastPayment(
                                        payment = paymentItem,
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                "Payment undone successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        onError = { exception: Exception ->
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = exception.message.toString(),
                                                    withDismissAction = true,
                                                    duration = SnackbarDuration.Indefinite
                                                )
                                            }
                                        },
                                    )

                                })
                        }
                    }
                }
            }
        }
    }
}