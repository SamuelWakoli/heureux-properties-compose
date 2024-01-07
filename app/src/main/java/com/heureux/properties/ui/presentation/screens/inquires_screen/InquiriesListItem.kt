package com.heureux.properties.ui.presentation.screens.inquires_screen

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InquiriesListItem() {
    ListItem(
        overlineContent = {
            Text(text = "12:30 pm     02/01/2024")
        },
        headlineContent = {
            Text(text = "Property Title")
        },
        supportingContent = {
            Text(text = "Ksh. 2,000,000")
        }
    )
}

@Preview
@Composable
private fun MyInquiresListItemPreview() {
    InquiriesListItem()
}