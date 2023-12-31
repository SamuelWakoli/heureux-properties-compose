package com.heureux.properties.ui.presentation.main.property_action_screens.property_details_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heureux.properties.ui.presentation.composables.images.CoilImage

@Composable
fun DetailsImageListItem(
    imageUrl: String = "https://picsum.photos/200",
) {
    Card(
        modifier = Modifier
            .padding(
                vertical = 4.dp, horizontal = 8.dp
            )
            .widthIn(min = 200.dp, max = 300.dp)
    ) {
        Column {
            CoilImage(
                imageUrl = imageUrl,
                modifier = Modifier.heightIn(
                    min = 400.dp,
                    max = 500.dp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsImageListItemPreview() {
    DetailsImageListItem()
}