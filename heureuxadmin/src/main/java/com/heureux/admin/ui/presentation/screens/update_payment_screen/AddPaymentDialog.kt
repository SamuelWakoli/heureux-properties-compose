package com.heureux.admin.ui.presentation.screens.update_payment_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddPaymentDialog(onDismissRequest: () -> Unit, onClickSave: (amount: String) -> Unit) {

    var amount by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    amount,
                    { value ->
                        amount = value
                        amountError = false
                    },
                    label = { Text(text = "Amount") },
                    leadingIcon = { Text(text = "Ksh", fontWeight = FontWeight.Bold) },
                    supportingText = {
                        if (amountError) Text(text = "Amount cannot be empty")
                    },
                    isError = amountError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (amount.isNotEmpty()) {
                                onDismissRequest()
                                onClickSave(amount)
                            } else amountError = true
                        }
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    TextButton(onClick = {
                        if (amount.isNotEmpty()) {
                            onDismissRequest()
                            onClickSave(amount)
                        } else amountError = true
                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}