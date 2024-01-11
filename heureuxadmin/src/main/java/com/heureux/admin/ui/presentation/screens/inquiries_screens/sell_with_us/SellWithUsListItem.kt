package com.heureux.admin.ui.presentation.screens.inquiries_screens.sell_with_us

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun SellWithUsListItem() {

    var showOptions by remember { mutableStateOf(false) }

    ListItem(modifier = Modifier.widthIn(max = 600.dp), overlineContent = {
        Text(text = "12:30pm  11/02/2024",
            color = MaterialTheme.colorScheme.secondary,)
    }, headlineContent = {
        Text(
            text = "Property Name",
            color = MaterialTheme.colorScheme.primary,
        )
    }, supportingContent = {
        Text(text = buildAnnotatedString {
            append("Username: ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("John Doe")
            }
        })

        Text(text = buildAnnotatedString {
            append("Price: ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("Ksh 2,000,000")
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
                text = { Text(text = "Call John Doe") },
                onClick = { showOptions = false /*TODO*/ })
            DropdownMenuItem(leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Image, contentDescription = null
                )
            }, text = { Text(text = "View images") }, onClick = { showOptions = false /*TODO*/ })
            // TODO: Hide this if property is archived
            DropdownMenuItem(leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Archive, contentDescription = null
                )
            }, text = { Text(text = "Archive") }, onClick = { showOptions = false /*TODO*/ })
            Divider(modifier = Modifier.padding(4.dp))
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Delete, contentDescription = null
                    )
                },
                text = { Text(text = "Delete") }, onClick = { showOptions = false /*TODO*/ },
                colors = MenuDefaults.itemColors(
                    leadingIconColor = MaterialTheme.colorScheme.error,
                    textColor = MaterialTheme.colorScheme.error,
                ),
            )

        }
    })
}