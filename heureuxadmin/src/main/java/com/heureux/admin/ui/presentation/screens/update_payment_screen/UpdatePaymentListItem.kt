package com.heureux.admin.ui.presentation.screens.update_payment_screen

import android.widget.Toast
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.heureux.admin.data.types.PaymentItem
import com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.users_screen.UsersScreenViewModel
import java.time.LocalDateTime

@Composable
fun UpdatePaymentListItem(
    viewModel: UsersScreenViewModel,
    paymentItem: PaymentItem,
    onClickAddPayment: (amount: String) -> Unit,
    onClickUndoPayment: () -> Unit,
) {
    var showOptions by remember { mutableStateOf(false) }
    var showAddPaymentDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val property = viewModel.getProperty(paymentItem.propertyId) {}.collectAsState().value

    ListItem(
        overlineContent = {
            val time = LocalDateTime.parse(paymentItem.time)
            Text(text = "Date: ${time.dayOfMonth}/${time.monthValue}/${time.year}    Time: ${time.hour}:${time.minute}")
        },
        headlineContent = { Text(text = property?.name.toString()) },
        supportingContent = {
            Text(
                text = "Agreed amount: Ksh. ${paymentItem.agreedPrice}\n" +
                        "Total paid: Ksh. ${paymentItem.totalAmountPaid}\n" +
                        "Owing: Ksh. ${paymentItem.owingAmount}\n\n" +
                        "Approved by: ${paymentItem.approvedBy.uppercase()}"
            )
        },
        trailingContent = {
            IconButton(onClick = { showOptions = true }) {
                Icon(
                    imageVector = if (showOptions) Icons.Outlined.Close else Icons.Outlined.MoreVert,
                    contentDescription = if (showOptions) "Hide" else "Options"
                )
            }

            DropdownMenu(
                expanded = showOptions,
                onDismissRequest = { showOptions = false }) {
                DropdownMenuItem(
                    text = { Text(text = "Add payment") },
                    onClick = { showAddPaymentDialog = true })
                DropdownMenuItem(
                    text = { Text(text = "Undo payment") },
                    onClick = { onClickUndoPayment() })
            }
        },
        modifier = Modifier.widthIn(max = 600.dp)
    )

    if (showAddPaymentDialog) AddPaymentDialog(
        onDismissRequest = {
            showAddPaymentDialog = false
        },
        onClickSave = { amount ->
            Toast.makeText(context, "Payment updating...", Toast.LENGTH_SHORT).show()
            onClickAddPayment(amount)
        },
    )
}