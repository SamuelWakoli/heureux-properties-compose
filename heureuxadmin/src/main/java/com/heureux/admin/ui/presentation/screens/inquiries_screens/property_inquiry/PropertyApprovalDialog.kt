package com.heureux.admin.ui.presentation.screens.inquiries_screens.property_inquiry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PropertyApprovalDialog(
    propertyName: String,
    buyerName: String,
    price: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onApprove: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(
                modifier = modifier.then(Modifier.padding(8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.Outlined.DoneAll, contentDescription = null)
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Do you want to approve purchase of $propertyName by $buyerName for $price?",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "After approval, go to 'users' to update payment",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = {
                        onApprove.invoke()
                        onDismissRequest.invoke()
                    }) {
                        Text(text = "Approve")
                    }
                }
            }
        }
    }
}