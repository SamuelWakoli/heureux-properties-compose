package com.heureux.admin.ui.presentation.screens.payment_history_screen

import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun PaymentHistoryListItem() {
    ListItem(
        overlineContent = {
            Text(text = "12:32am 12/01/2024")
        },
        headlineContent = {
            Text(text = "username username")
        },
        supportingContent = {
            Text(text = buildAnnotatedString {
                append("Property name: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("property name")
                }
            })
            Text(text = buildAnnotatedString {
                append("Payment method: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Mortgage")
                }
            })
            Text(text = buildAnnotatedString {
                append("Amount paid: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Ksh 200,000")
                }
            })
            Text(text = buildAnnotatedString {
                append("Total price: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Ksh 2,000,000")
                }
            })
            Text(text = buildAnnotatedString {
                append("Owing: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Ksh 800,000")
                }
            })
            Text(text = buildAnnotatedString {
                append("Approved by: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("admin name")
                }
            })

        })
}