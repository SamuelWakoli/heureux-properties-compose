package com.heureux.admin.ui.presentation.screens.payment_history_screen

import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.heureux.admin.data.types.PaymentItem
import java.time.LocalDateTime

@Composable
fun PaymentHistoryListItem(
    paymentItem: PaymentItem,
    username: String,
    propertyName: String,
) {
    ListItem(
        overlineContent = {
            val time = LocalDateTime.parse(paymentItem.time)
            Text(text = "Date: ${time.dayOfMonth}/${time.monthValue}/${time.year}    Time: ${time.hour}:${time.minute}")
        },
        headlineContent = {
            Text(text = username)
        },
        supportingContent = {
            Text(text = buildAnnotatedString {
                append("Property name: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(propertyName)
                }
            })
            Text(text = buildAnnotatedString {
                append("Payment method: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(paymentItem.paymentMethod)
                }
            })
            Text(text = buildAnnotatedString {
                append("Amount paid: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Ksh ${paymentItem.totalAmountPaid}")
                }
            })
            Text(text = buildAnnotatedString {
                append("Total price: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Ksh ${paymentItem.agreedPrice}")
                }
            })
            Text(text = buildAnnotatedString {
                append("Owing: ")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Ksh ${paymentItem.owingAmount}")
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