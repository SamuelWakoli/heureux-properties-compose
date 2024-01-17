package com.heureux.properties.ui.presentation.screens.payment_history_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.data.types.PaymentItem
import java.time.LocalDateTime

@Composable
fun PaymentListItem(
    property: HeureuxProperty?,
    paymentItem: PaymentItem
) {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        border = CardDefaults.outlinedCardBorder()
    ) {
        ListItem(
            modifier = Modifier.widthIn(min = 400.dp, max = 600.dp),
            headlineContent = {
                Text(text = "Amount: Ksh. ${paymentItem.amount}")
            },
            supportingContent = {
                val propertyName = property?.name ?: ""
                val dateTime = LocalDateTime.parse(paymentItem.time)

                Text(
                    text = "$propertyName\n" +
                            "Agreed Price: Ksh. ${paymentItem.agreedPrice}\n" +
                            "Total Amount Paid: Ksh. ${paymentItem.totalAmountPaid}\n" +
                            "Owing: Ksh. ${paymentItem.owingAmount}\n" +
                            "Date: ${dateTime.dayOfMonth}/${dateTime.monthValue}/${dateTime.year} Time: ${dateTime.hour}:${dateTime.minute}"
                )
            }
        )
    }
}