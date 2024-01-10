package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.users_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.heureux.admin.data.types.UserProfileData
import com.heureux.admin.ui.presentation.composables.images.CoilImage
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreenViewModel

@Composable
fun UsersScreen(
    bottomNavHostController: NavHostController,
    mainNavHostController: NavController,
    viewModel: MainScreenViewModel,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(20){
            HeureuxUserListItem()
        }
    }
}



@Composable
fun HeureuxUserListItem(
    userData: UserProfileData? = UserProfileData(
        userID = "user@gmail.com",
        displayName = "username",
        photoURL = null,
        userEmail = "user@gmail.com",
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
        modifier = Modifier
            .widthIn(max = 600.dp)
            .clickable {
                // TODO: show contextual menu
            }
    )
}