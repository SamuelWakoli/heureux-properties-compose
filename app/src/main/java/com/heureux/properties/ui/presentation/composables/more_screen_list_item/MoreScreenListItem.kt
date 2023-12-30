package com.heureux.properties.ui.presentation.composables.more_screen_list_item

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MoreScreenListItem(
    leadingIcon: (@Composable () -> Unit)? = null,
    headlineContent: (@Composable () -> Unit)? = null,
    supportingContent: (@Composable () -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    ListItem(
        modifier = Modifier.clickable {
            onClick()
        },
        leadingContent =
            if (leadingIcon != null) {
                { leadingIcon() }
            } else null
        ,
        headlineContent = {
            if (headlineContent != null) {
                headlineContent()
            }
        },
        supportingContent =
        if (supportingContent != null) {
            { supportingContent() }
        } else null,
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
            leadingIconColor = MaterialTheme.colorScheme.primary,
            headlineColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Preview
@Composable
private fun MoreScreenListItemPreview() {
    MoreScreenListItem(
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = null)
        },
        headlineContent = {
            Text(text = "Profile")
        },
    )
}