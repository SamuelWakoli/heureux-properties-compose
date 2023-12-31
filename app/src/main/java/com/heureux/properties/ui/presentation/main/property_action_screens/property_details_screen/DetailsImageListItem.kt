package com.heureux.properties.ui.presentation.main.property_action_screens.property_details_screen

import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heureux.properties.ui.presentation.composables.images.CoilImage

@Composable
fun DetailsImageListItem(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
) {
    CoilImage(
//        imageUrl = imageUrl,
        modifier = modifier
            .heightIn(
                min = 160.dp, max = 220.dp
            )
            .then(modifier)
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailsImageListItemPreview() {
    DetailsImageListItem()
}