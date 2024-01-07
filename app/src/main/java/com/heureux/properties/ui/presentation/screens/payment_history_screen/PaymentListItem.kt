package com.heureux.properties.ui.presentation.screens.payment_history_screen

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PaymentListItem() {
    ListItem(
        modifier = Modifier.widthIn(min = 400.dp, max = 600.dp),
        overlineContent = {
            Text(text = "Paid to: Heureux Properties")
        },
        headlineContent = {
            Text(text = "Amount: Ksh. 200,000")
        },
        supportingContent = {
            Text(
                text = "Property: Property Name\n" +
                        "Agreed Price: Ksh. 1,200,000\n" +
                        "Total Amount Paid: Ksh. 800,000\n" +
                        "Owing: Ksh. 400,000\n" +
                        "Date: 12/02/2024"
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PaymentListItemPreview() {
    PaymentListItem()
}