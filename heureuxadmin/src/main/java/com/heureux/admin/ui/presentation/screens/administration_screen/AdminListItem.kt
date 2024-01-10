package com.heureux.admin.ui.presentation.screens.administration_screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.heureux.admin.data.types.UserProfileData
import com.heureux.admin.ui.presentation.composables.images.CoilImage

@Composable
fun AdminListItem(
    userData: UserProfileData? = UserProfileData(
        userID = "admin@gmail.com",
        displayName = "Admin Name",
        photoURL = null,
        userEmail = "admin@gmail.com",
        phone = "0712345678"
    )
) {
    ListItem(
        leadingContent = {
            if (userData?.photoURL != null && userData.photoURL.toString() != "null") {
                CoilImage(
                    modifier = Modifier.size(64.dp),
                    imageUrl = userData.photoURL.toString(),
                    applyCircleShape = true,
                    errorContent = {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier.size(64.dp)
                        )
                    },
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Profile image",
                    modifier = Modifier.size(64.dp),
                )
            }
        },
        overlineContent = {
            Text(text = userData?.phone ?: "")
        },
        headlineContent = {
            Text(text = userData?.displayName ?: "")
        },
        supportingContent = {
            Text(text = userData?.userEmail ?: "")
        },
        trailingContent = {
            Row{
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Phone, contentDescription = "call")
                }
                Spacer(modifier = Modifier.size(4.dp))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Whatsapp, contentDescription = "whatsapp", tint = Color.Green)
                }
            }
        },
        colors = ListItemDefaults.colors(
            leadingIconColor = MaterialTheme.colorScheme.primary,
            headlineColor = MaterialTheme.colorScheme.primary,
            supportingColor = MaterialTheme.colorScheme.primary,
            trailingIconColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier.widthIn(max = 600.dp)
    )
}