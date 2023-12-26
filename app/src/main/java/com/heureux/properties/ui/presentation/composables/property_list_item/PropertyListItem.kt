package com.heureux.properties.ui.presentation.composables.property_list_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Cases
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
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
                modifier = Modifier
                    .heightIn(
                        min = 160.dp, max = 220.dp
                    )
            )
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent
                ),
                overlineContent = {
                    Text(text = "Price: Ksh. 2,000,000")
                },
                headlineContent = {
                    Text(text = "Property Name")
                },
                supportingContent = {
                    Column {
                        Text(
                            text = "Property Description Property Description Property Description Property Description Property Description Property Description Property Description Property Description ",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Outlined.BookmarkAdd,
                                    contentDescription = "Bookmark",
                                    modifier = Modifier.size(26.dp),
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically,
                            )
                            {
                                TextButton(onClick = { /*TODO*/ }) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,

                                        ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(text = "Details")
                                    }
                                }
                                Spacer(modifier = Modifier.size(16.dp))
                                TextButton(onClick = { /*TODO*/ }) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,

                                        ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Cases,
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(text = "Inquire")
                                    }
                                }
                            }
                        }
                    }
                }
            )

        }


    }
}

@Preview
@Composable
private fun PropertyListItemPreview() {
    PropertyListItem()
}