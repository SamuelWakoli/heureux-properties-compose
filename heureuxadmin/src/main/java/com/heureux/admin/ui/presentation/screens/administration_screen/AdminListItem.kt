package com.heureux.admin.ui.presentation.screens.administration_screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.heureux.admin.data.types.UserProfileData
import com.heureux.admin.ui.presentation.composables.images.CoilImage
import com.heureux.admin.utils.formatPhoneNumber

@Composable
fun AdminListItem(
    userData: UserProfileData? = UserProfileData(
        displayName = "Admin Name",
        photoURL = null,
        userEmail = "admin@gmail.com",
        phone = "0712345678"
    )
) {
    val context = LocalContext.current

    ListItem(leadingContent = {
        if (userData?.photoURL != null && userData.photoURL.toString() != "null") {
            CoilImage(
                modifier = Modifier.size(64.dp),
                imageUrl = userData.photoURL.toString(),
                applyCircleShape = true,
                showOpenImageButton = false,
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
    }, overlineContent = {
        Text(
            text = if (userData?.phone == "null" || userData?.phone == null || userData.phone.isEmpty())
                "Phone number not set" else userData.phone
        )
    }, headlineContent = {
        Text(text = userData?.displayName ?: "")
    }, supportingContent = {
        Text(text = userData?.userEmail ?: "")
    }, trailingContent = {
        if (userData?.phone != "null" && userData?.phone != null)
            Row {
                IconButton(onClick = {
                    val uri = "tel:${userData.phone}"
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse(uri)
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Phone not found: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }) {
                    Icon(imageVector = Icons.Outlined.Phone, contentDescription = "call")
                }
                Spacer(modifier = Modifier.size(4.dp))
                IconButton(onClick = {
                    val uri = "https://wa.me/${formatPhoneNumber(userData.phone, context)}"
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(uri)
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Whatsapp,
                        contentDescription = "whatsapp",
                        tint = Color.Green
                    )
                }
            }
    }, colors = ListItemDefaults.colors(
        leadingIconColor = MaterialTheme.colorScheme.primary,
        headlineColor = MaterialTheme.colorScheme.primary,
        supportingColor = MaterialTheme.colorScheme.primary,
        trailingIconColor = MaterialTheme.colorScheme.primary,
    ), modifier = Modifier.widthIn(max = 600.dp)
    )
}