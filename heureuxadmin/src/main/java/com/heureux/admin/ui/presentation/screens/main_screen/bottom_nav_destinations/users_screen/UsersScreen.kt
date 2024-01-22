package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.users_screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.heureux.admin.data.types.HeureuxUser
import com.heureux.admin.ui.presentation.composables.images.CoilImage
import com.heureux.admin.ui.presentation.navigation.Screens
import com.heureux.admin.utils.formatPhoneNumber
import com.heureux.admin.utils.openImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    navController: NavController,
    viewModel: UsersScreenViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val allUsers = viewModel.allUsers.collectAsState().value

    Column(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
    ) {

        if (allUsers == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                    strokeWidth = 2.dp
                )
            }
        } else if (allUsers.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Groups,
                    contentDescription = null,
                    modifier = Modifier.size(128.dp),
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "No user found",
                    style = MaterialTheme.typography.headlineSmall
                )

            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(allUsers) { user ->
                    HeureuxUserListItem(
                        user = user,
                        navController = navController
                    ) {
                        viewModel.updateCurrentUser(user)
                    }
                }
            }
        }
    }
}


@Composable
fun HeureuxUserListItem(
    user: HeureuxUser,
    navController: NavController,
    onClickUpdatePayment: () -> Unit
) {
    var showOptions by remember { mutableStateOf(false) }
    val context = navController.context

    ListItem(
        leadingContent = {
            if (user.photoUrl != null && user.photoUrl.toString() != "null") {
                CoilImage(
                    modifier = Modifier.size(64.dp),
                    imageUrl = user.photoUrl.toString(),
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
        },
        overlineContent = {
            Text(text = user.phone ?: "Phone not set by this user")
        },
        headlineContent = {
            Text(text = user.name ?: "")
        },
        supportingContent = {
            Text(text = user.email ?: "")
        },
        trailingContent = {
            IconButton(onClick = { showOptions = true }) {
                if (showOptions)
                    Icon(imageVector = Icons.Outlined.Close, contentDescription = "Hide")
                else Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "Options")
            }

            DropdownMenu(expanded = showOptions, onDismissRequest = { showOptions = false }) {
                if (user.phone != null) {
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Call, contentDescription = null)
                        },

                        text = { Text(text = "Call ${user.name}") },
                        onClick = {
                            showOptions = false
                            val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:${user.phone}")
                            }
                            try {
                                context.startActivity(phoneIntent)
                            } catch (e: ActivityNotFoundException) {
                                // Handle error if no phone app is found
                                Toast.makeText(context, "No phone app found", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Whatsapp, contentDescription = null)
                        },
                        text = { Text(text = "Chat on WhatsApp") },
                        onClick = {
                            showOptions = false
                            val phone = formatPhoneNumber(user.phone, context)

                            val whatsAppIntent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://api.whatsapp.com/send?phone=$phone")
                            }
                            try {
                                context.startActivity(whatsAppIntent)
                            } catch (e: ActivityNotFoundException) {
                                // Handle error if no phone app is found
                                Toast.makeText(context, "No WhatsApp app found", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Sms, contentDescription = null)
                        },
                        text = { Text(text = "Send message (sms)") },
                        onClick = {
                            showOptions = false
                            val phone = formatPhoneNumber(user.phone, context)
                            val smsIntent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("sms:$phone")
                            }
                            try {
                                context.startActivity(smsIntent)
                            } catch (e: ActivityNotFoundException) {
                                // Handle error if no phone app is found
                                Toast.makeText(context, "No SMS app found", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })
                    if (!user.photoUrl.isNullOrEmpty())
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.AccountBox,
                                    contentDescription = null
                                )
                            },
                            text = { Text(text = "View profile image") },
                            onClick = {
                                openImage(context, user.photoUrl.toUri()) { exception ->
                                    Toast
                                        .makeText(context, exception.message, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            })
                    Divider(
                        modifier = Modifier.padding(4.dp)
                    )
                }
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
                        showOptions = false
                        onClickUpdatePayment()
                        navController.navigate(Screens.UpdatePaymentScreen.route) {
                            launchSingleTop = true
                        }
                    })
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