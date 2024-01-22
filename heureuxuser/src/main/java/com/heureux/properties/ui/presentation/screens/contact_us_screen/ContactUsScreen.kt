package com.heureux.properties.ui.presentation.screens.contact_us_screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsScreen(
    navController: NavController,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.SupportAgent, contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Contact us")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .padding(16.dp)
            ) {
                ContactListItem(
                    title = "Email",
                    description = "info@heureuxproperties.co.ke",
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    },
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:info@heureuxproperties.co.ke")
                        }
                        try {
                            context.startActivity(emailIntent)
                        } catch (e: ActivityNotFoundException) {
                            // Handle error if no email app is found
                            Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
                        }
                    },
                )
                ContactListItem(
                    title = "Call us",
                    description = "+254704606003",
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Phone, contentDescription = null)
                    },
                    onClick = {
                        val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:+254704606003")
                        }
                        try {
                            context.startActivity(phoneIntent)
                        } catch (e: ActivityNotFoundException) {
                            // Handle error if no phone app is found
                            Toast.makeText(context, "No phone app found", Toast.LENGTH_SHORT).show()
                        }
                    },
                    leadingIconColor = Color.Cyan
                )
                ContactListItem(
                    title = "WhatsApp",
                    description = "+254704606003",
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Whatsapp, contentDescription = null)
                    },
                    onClick = {
                        val whatsAppIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://api.whatsapp.com/send?phone=+254704606003")
                        }
                        try {
                            context.startActivity(whatsAppIntent)
                        } catch (e: ActivityNotFoundException) {
                            // Handle error if WhatsApp is not installed
                            Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    leadingIconColor = Color.Green
                )
                ContactListItem(
                    title = "Feedback",
                    description = "In-app",
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Feedback, contentDescription = null)
                    },
                    onClick = {
                        navController.navigate(Screens.FeedbackScreen.route) {
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun ContactUsScreenPreview() {
    ContactUsScreen(navController = rememberNavController())
}

@Composable
fun ContactListItem(
    title: String,
    description: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
    leadingIconColor: Color? = null,
) {
    ListItem(
        modifier = Modifier.clickable { onClick() },
        leadingContent = leadingIcon,
        headlineContent = {
            Text(text = title)
        },
        supportingContent = {
            Text(
                text = description, style = TextStyle(
                    textDecoration = TextDecoration.Underline
                )
            )
        },
        colors = ListItemDefaults.colors(
            leadingIconColor = leadingIconColor ?: MaterialTheme.colorScheme.primary,
            headlineColor = MaterialTheme.colorScheme.primary,
            supportingColor = MaterialTheme.colorScheme.secondary
        ),
    )
}