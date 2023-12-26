package com.heureux.properties.ui.presentation.composables.property_list_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heureux.properties.ui.presentation.composables.images.CoilImage

@Composable
fun PropertyListItem() {
    Card(
        modifier = Modifier
            .padding(
                vertical = 4.dp, horizontal = 8.dp
            )
            .widthIn(min = 400.dp, max = 600.dp)
    ) {
        Column {
            CoilImage(
                modifier = Modifier.heightIn(
                    min = 160.dp, max = 260.dp
                )

            )
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent
                ),
                headlineContent = {
                    Text(text = "Property Name")
                })
        }


    }
}

@Preview
@Composable
private fun PropertyListItemPreview() {
    PropertyListItem()
}