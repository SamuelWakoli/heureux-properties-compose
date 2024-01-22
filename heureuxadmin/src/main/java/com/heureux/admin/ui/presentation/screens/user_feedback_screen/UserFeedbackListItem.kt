package com.heureux.admin.ui.presentation.screens.user_feedback_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DoneAll
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
import androidx.compose.ui.unit.dp
import com.heureux.admin.data.types.FeedbackItem

@Composable
fun UserFeedbackListItem(
    feedback: FeedbackItem,
    onMarkAsRead: () -> Unit,
    onDelete: () -> Unit
) {

    var showOptions: Boolean by remember { mutableStateOf(false) }

    ListItem(
        modifier = Modifier.widthIn(max = 600.dp),
        overlineContent = {
            Text(text = feedback.time)
        },
        headlineContent = { Text(text = feedback.senderEmail) },
        supportingContent = { Text(text = feedback.message) },
        trailingContent = {
            IconButton(onClick = { showOptions = true }) {
                Icon(
                    imageVector = if (showOptions) Icons.Outlined.Close else Icons.Outlined.MoreVert,
                    contentDescription = if (showOptions) "Hide" else "Options"
                )
            }
            DropdownMenu(expanded = showOptions, onDismissRequest = { showOptions = false }) {
                if (!feedback.read) {
                    DropdownMenuItem(trailingIcon = {
                        Icon(imageVector = Icons.Outlined.DoneAll, contentDescription = null)
                    }, text = { Text(text = "Mark as read") }, colors = MenuDefaults.itemColors(
                        leadingIconColor = MaterialTheme.colorScheme.primary,
                        textColor = MaterialTheme.colorScheme.primary,
                    ), onClick = {
                        showOptions = false
                        onMarkAsRead.invoke()
                    })
                    Divider(modifier = Modifier.padding(4.dp))
                }
                DropdownMenuItem(leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                }, text = { Text(text = "Delete") }, colors = MenuDefaults.itemColors(
                    leadingIconColor = MaterialTheme.colorScheme.error,
                    textColor = MaterialTheme.colorScheme.error,
                ), onClick = {
                    showOptions = false
                    onDelete.invoke()
                })
            }
        },
    )
}