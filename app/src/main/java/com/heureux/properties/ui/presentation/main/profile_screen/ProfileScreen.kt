package com.heureux.properties.ui.presentation.main.profile_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.NoAccounts
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    mainNavController: NavController,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { mainNavController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back",
                        )
                    }
                },
                title = { Text(text = "Profile") },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(paddingValues)
        ) {
            Text(
                text = "My details",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "Profile image",
                        modifier = Modifier.size(64.dp),
                    )
                },
                headlineContent = {
                    Text(text = "username")
                },
                supportingContent = {
                    Text(text = "username@gmail.com")
                },
            )
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null,
                    )
                },
                headlineContent = {
                    Text(text = "Phone")
                },
                supportingContent = {
                    Text(text = "0712345678")
                },
            )
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "Records",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp),
            )
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Business,
                        contentDescription = null,
                    )
                },
                headlineContent = {
                    Text(text = "My properties")
                },
                modifier = Modifier.clickable {
                    // TODO: Navigate to my properties screen
                }
            )
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Store,
                        contentDescription = null,
                    )
                },
                headlineContent = {
                    Text(text = "Sold properties")
                },
                modifier = Modifier.clickable {
                    // TODO: Navigate to history screen
                }
            )
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.History,
                        contentDescription = null,
                    )
                },
                headlineContent = {
                    Text(text = "Payment history")
                },
                modifier = Modifier.clickable {
                    // TODO: Navigate to history screen
                }
            )
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "Actions",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp),
            )
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null,
                    )
                },
                headlineContent = {
                    Text(text = "Edit profile")
                },
                modifier = Modifier.clickable {
                    // TODO: Navigate to edit profile screen
                }
            )
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Logout,
                        contentDescription = null,
                    )
                },
                headlineContent = {
                    Text(text = "Sign out")
                },
                modifier = Modifier.clickable {
                    // TODO: Sign out user
                }
            )
            ListItem(
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.NoAccounts,
                        contentDescription = null,
                    )
                },
                headlineContent = {
                    Text(text = "Delete profile & data")
                },
                modifier = Modifier.clickable {
                    // TODO: Delete user data and sign out
                }
            )

        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        mainNavController = rememberNavController()
    )
}