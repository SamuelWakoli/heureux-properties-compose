package com.heureux.admin.ui.presentation.screens.inquiries_screens.sell_with_us

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.admin.data.types.SellWithUsRequest
import com.heureux.admin.ui.presentation.navigation.Screens
import com.heureux.admin.ui.presentation.screens.inquiries_screens.InquiriesViewModel
import java.time.LocalDateTime

@Composable
fun SellWithUsListItem(
    sellWithUsRequest: SellWithUsRequest,
    navController: NavController,
    viewModel: InquiriesViewModel,
) {

    val allUsers = viewModel.allUsers.collectAsState().value
    val user = allUsers?.find { it.email == sellWithUsRequest.userId }

    var showOptions by remember { mutableStateOf(false) }
    val context = navController.context

    ListItem(modifier = Modifier.widthIn(max = 600.dp), overlineContent = {
        val time = LocalDateTime.parse(sellWithUsRequest.time)
        Text(
            text = "Date: ${time.dayOfMonth}/${time.monthValue}/${time.year}    Time: ${time.hour}:${time.minute}",
            color = MaterialTheme.colorScheme.secondary,
        )
    }, headlineContent = {
        Text(
            text = sellWithUsRequest.propertyName,
            color = MaterialTheme.colorScheme.primary,
        )
    }, supportingContent = {
        Text(text = buildAnnotatedString {
            append("Username: ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(user?.name.toString())
            }
        })

        Text(text = buildAnnotatedString {
            append("Price: ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("Ksh ${sellWithUsRequest.propertyPrice}")
            }
        })
    }, trailingContent = {
        IconButton(onClick = { showOptions = true }) {
            Icon(
                imageVector = if (showOptions) Icons.Outlined.Close else Icons.Outlined.MoreVert,
                contentDescription = if (showOptions) "Hide" else "Options"
            )
        }

        DropdownMenu(expanded = showOptions, onDismissRequest = { showOptions = false }) {
            DropdownMenuItem(leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Call, contentDescription = null
                )
            },
                text = { Text(text = "Call ${user?.name}") },
                onClick = {
                    showOptions = false
                    val phone = user?.phone
                    if (phone != null && phone != "null") {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$phone")
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                context, "No phone app found", Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {
                        Toast.makeText(
                            context, "This user has not added phone number", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            DropdownMenuItem(leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Image, contentDescription = null
                )
            }, text = { Text(text = "View images") }, onClick = {
                showOptions = false
                viewModel.updateCurrentImagesList(sellWithUsRequest.propertyImages)
                navController.navigate(Screens.SellWithUsImagesScreen.route) {
                    launchSingleTop = true
                }
            })
            if (!sellWithUsRequest.archived)
                DropdownMenuItem(leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Archive, contentDescription = null
                    )
                }, text = { Text(text = "Archive") }, onClick = {
                    showOptions = false
                    viewModel.updateArchiveSellWithUsInquiry(
                        sellWithUsRequest,
                        onSuccess = {
                            Toast.makeText(
                                context, "Archived", Toast.LENGTH_SHORT
                            ).show()
                        },
                        onFailure = {
                            Toast.makeText(
                                context, "Failed to archive", Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                })
            Divider(modifier = Modifier.padding(4.dp))
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Delete, contentDescription = null
                    )
                },
                text = { Text(text = "Delete") },
                onClick = {
                    showOptions = false
                    viewModel.deleteSellWithUsInquiry(
                        sellWithUsRequest,
                        onSuccess = {
                            Toast.makeText(
                                context, "Deleted", Toast.LENGTH_SHORT
                            ).show()
                        },
                        onFailure = {
                            Toast.makeText(
                                context, "Failed to delete", Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                },
                colors = MenuDefaults.itemColors(
                    leadingIconColor = MaterialTheme.colorScheme.error,
                    textColor = MaterialTheme.colorScheme.error,
                ),
            )

        }
    })
}