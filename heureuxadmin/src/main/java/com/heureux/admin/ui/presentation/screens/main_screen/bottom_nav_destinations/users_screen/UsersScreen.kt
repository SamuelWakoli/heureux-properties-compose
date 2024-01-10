package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.users_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.heureux.admin.data.types.UserProfileData
import com.heureux.admin.ui.presentation.composables.images.CoilImage
import com.heureux.admin.ui.presentation.navigation.Screens
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
        items(20) {
            HeureuxUserListItem(
                navController = mainNavHostController
            )
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
    ),
    navController: NavController
) {
    var showOptions by remember { mutableStateOf(false) }

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
            IconButton(onClick = { showOptions = true }) {
                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "Options")
            }

            if (showOptions) {
                DropdownMenu(expanded = showOptions, onDismissRequest = { showOptions = false }) {
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Call, contentDescription = null)
                        },

                        text = { Text(text = "Call Username"/*Username == user firstname*/) },
                        onClick = { showOptions = false/*TODO*/ })
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Whatsapp, contentDescription = null)
                        },
                        text = { Text(text = "Chat on WhatsApp") },
                        onClick = { showOptions = false/*TODO*/ })
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Sms, contentDescription = null)
                        },
                        text = { Text(text = "Send message (sms)") },
                        onClick = { showOptions = false/*TODO*/ })
                    Divider(
                        modifier = Modifier.padding(4.dp)
                    )
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Payments, contentDescription = null)
                        },
                        text = { Text(text = "Update payment") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.ArrowForwardIos,
                                contentDescription = null,
                                modifier = Modifier.size(12.dp)
                            )
                        },
                        onClick = {
                            showOptions = false/*TODO*/
                            navController.navigate(Screens.UpdatePaymentScreen.route) {
                                launchSingleTop = true
                            }
                        })
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
    )
}