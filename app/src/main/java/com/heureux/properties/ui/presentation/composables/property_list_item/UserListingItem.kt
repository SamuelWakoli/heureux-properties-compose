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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.presentation.composables.images.CoilImage
import com.heureux.properties.ui.presentation.navigation.Screens

@Composable
fun UserListingItem(
    navController: NavController,
    onClickDelete: () -> Unit = {},
) {
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
                    min = 160.dp, max = 220.dp
                )
            )
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Price: Ksh. 2,000,000",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Property Name",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    text = "Property Description Property Description Property Description Property Description Property Description Property Description Property Description Property Description ",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = {
                        onClickDelete()
                        /*TODO*/
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(26.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextButton(onClick = {
                            /*TODO*/
                            navController.navigate(Screens.PropertyDetailsScreen.route) {
                                launchSingleTop = true
                            }
                        }) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                Icon(
                                    imageVector = Icons.Outlined.Info, contentDescription = null
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(text = "Details")
                            }
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        TextButton(onClick = {
                            /*TODO*/
                            navController.navigate(Screens.EditPropertyScreen.route) {
                                launchSingleTop = true
                            }
                        }) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                Icon(
                                    imageVector = Icons.Outlined.EditNote, contentDescription = null
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(text = "Edit")
                            }
                        }
                    }
                }
            }

        }


    }
}

@Preview
@Composable
private fun UserListingItemPreview() {
    UserListingItem(
        navController = rememberNavController()
    )
}