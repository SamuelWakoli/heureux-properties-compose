package com.heureux.admin.ui.presentation.composables.buttons

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun RadioButtonListItem(
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
    RadioButtonListItem(
        label = "Full payment",
        currentValue = "Full payment",
        onClick = {},
    )
}