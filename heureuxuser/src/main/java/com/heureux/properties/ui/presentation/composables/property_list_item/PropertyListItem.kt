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
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Cases
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.properties.data.types.HeureuxProperty
import com.heureux.properties.ui.presentation.composables.images.CoilImage
import com.heureux.properties.ui.presentation.navigation.Screens

@Composable
fun PropertyListItem(
    isSold: Boolean,
    property: HeureuxProperty,
    navController: NavController,
    onUpdateCurrentProperty: () -> Unit,
    isBookmarked: Boolean,
    onClickBookmark: () -> Unit,
) {


    Card(
        modifier = Modifier
            .padding(
                vertical = 4.dp, horizontal = 8.dp
            )
            .widthIn(min = 400.dp, max = 600.dp)
    ) {
        Column {
            if (property.imageUrls.isEmpty()) {
                Text(
                    text = "No Image Added",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                CoilImage(
                    imageUrl = property.imageUrls.first(),
                    modifier = Modifier
                        .heightIn(
                            min = 160.dp, max = 220.dp
                        )
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

                if (isSold) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(top = 8.dp, start = 8.dp),
                        shape = MaterialTheme.shapes.small,
                        border = CardDefaults.outlinedCardBorder()
                    ) {
                        Text(
                            text = "SOLD",
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                Text(
                    text = "Price: Ksh. ${property.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )

                Text(
                    text = property.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    text = property.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    if (isSold) {
                        TextButton(
                            onClick = { onClickBookmark() },
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(text = "Remove Bookmark")
                            }
                        }
                    } else {
                        IconButton(onClick = {
                            onClickBookmark()
                        }) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Filled.Bookmarks else Icons.Outlined.BookmarkAdd,
                                contentDescription = if (isBookmarked) "Remove from bookmarks" else "Add to bookmarks",
                                modifier = Modifier.size(26.dp),
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                    if (!isSold) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                        )
                        {
                            TextButton(onClick = {
                                onUpdateCurrentProperty()
                                navController.navigate(Screens.PropertyDetailsScreen.route) {
                                    launchSingleTop = true
                                }
                            }) {
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
                            TextButton(onClick = {
                                onUpdateCurrentProperty()
                                navController.navigate(Screens.InquiryScreen.route) {
                                    launchSingleTop = true
                                }
                            }) {
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


        }


    }
}