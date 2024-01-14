package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.inquiries_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Cases
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.admin.ui.presentation.navigation.Screens
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InquiriesScreen(
    navController: NavController,
    viewModel: MainScreenViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ListItem(
            modifier = Modifier
                .clickable {
                    navController.navigate(Screens.PropertyInquiryScreen.route) {
                        launchSingleTop = true
                    }
                }
                .widthIn(max = 600.dp),
            leadingContent = {
                Icon(imageVector = Icons.Outlined.Business, contentDescription = null)
            },
            headlineContent = {
                Text(text = "Property inquiry", style = MaterialTheme.typography.titleMedium)
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            },
            colors = ListItemDefaults.colors(
                leadingIconColor = MaterialTheme.colorScheme.primary,
                headlineColor = MaterialTheme.colorScheme.primary,
                trailingIconColor = MaterialTheme.colorScheme.primary,
            )
        )
        ListItem(
            modifier = Modifier
                .clickable {
                    navController.navigate(Screens.SellWithUsRequestsScreen.route) {
                        launchSingleTop = true
                    }
                }
                .widthIn(max = 600.dp),
            leadingContent = {
                Icon(imageVector = Icons.Outlined.Cases, contentDescription = null)
            },
            headlineContent = {
                Text(text = "Sell with us requests", style = MaterialTheme.typography.titleMedium)
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            },
            colors = ListItemDefaults.colors(
                leadingIconColor = MaterialTheme.colorScheme.primary,
                headlineColor = MaterialTheme.colorScheme.primary,
                trailingIconColor = MaterialTheme.colorScheme.primary,
            )
        )
        ListItem(
            modifier = Modifier
                .clickable {
                    navController.navigate(Screens.ArchivedPropertyInquiryScreen.route) {
                        launchSingleTop = true
                    }
                }
                .widthIn(max = 600.dp),
            leadingContent = {
                Icon(imageVector = Icons.Outlined.Archive, contentDescription = null)
            },
            headlineContent = {
                Text(
                    text = "Archived property inquiry",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            },
            colors = ListItemDefaults.colors(
                leadingIconColor = MaterialTheme.colorScheme.primary,
                headlineColor = MaterialTheme.colorScheme.primary,
                trailingIconColor = MaterialTheme.colorScheme.primary,
            )
        )
        ListItem(
            modifier = Modifier
                .clickable {
                    navController.navigate(Screens.ArchivedSellWithUsReqScreen.route) {
                        launchSingleTop = true
                    }
                }
                .widthIn(max = 600.dp),
            leadingContent = {
                Icon(imageVector = Icons.Outlined.Archive, contentDescription = null)
            },
            headlineContent = {
                Text(
                    text = "Archived sell with us requests",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            },
            colors = ListItemDefaults.colors(
                leadingIconColor = MaterialTheme.colorScheme.primary,
                headlineColor = MaterialTheme.colorScheme.primary,
                trailingIconColor = MaterialTheme.colorScheme.primary,
            )
        )
    }
}
