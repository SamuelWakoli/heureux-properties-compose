package com.heureux.properties.ui.presentation.main.bottom_bar_destinations

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Cases
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.presentation.composables.bottom_sheet.DynamicThemeBottomSheet
import com.heureux.properties.ui.presentation.composables.dialogs.ThemeSelectionDialog
import com.heureux.properties.ui.presentation.composables.more_screen_list_item.MoreScreenListItem
import com.heureux.properties.ui.presentation.navigation.Screens
import com.heureux.properties.ui.presentation.viewmodels.AppViewModelProvider
import com.heureux.properties.ui.presentation.viewmodels.MoreScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    moreScreenViewModel: MoreScreenViewModel,
    mainNavController: NavController,
) {

    val uiState = moreScreenViewModel.uiState.collectAsState().value

    val currentThemeData =
        moreScreenViewModel.currentThemeData.collectAsState(initial = "Light").value
    val dynamicColorState =
        moreScreenViewModel.currentDynamicColorState.collectAsState(initial = false).value

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection)
            .verticalScroll(state = scrollState)
            .padding(horizontal = 8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = 600.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "Profile")
                },
                onClick = {
                    mainNavController.navigate(Screens.ProfileScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Notifications, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "Notifications")
                },
                onClick = {
                    mainNavController.navigate(Screens.NotificationsScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Sell, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "Sell with us")
                },
                onClick = {
                    mainNavController.navigate(Screens.AddPropertyScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Cases, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "My inquiries")
                },
                onClick = {
                    mainNavController.navigate(Screens.MyInquiresScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Payments, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "Payment history")
                },
                onClick = {
                    mainNavController.navigate(Screens.PaymentHistoryScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
            Divider()
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "About us")
                },
                onClick = {
                    mainNavController.navigate(Screens.AboutUsScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "Privacy policy")
                }
            )
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.SupportAgent, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "Contact us")
                },
                onClick = {
                    mainNavController.navigate(Screens.ContactUsScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.RateReview, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "Rate us")
                },
                onClick = {
                    // intent used for testing purposes
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps"))
                    context.startActivity(intent)
                }
            )
            Divider()
            MoreScreenListItem(
                leadingIcon = {
                    Icon(
                        imageVector =
                        if (isSystemInDarkTheme()) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                        contentDescription = null
                    )
                },
                headlineContent = {
                    Text(text = "Theme")
                },
                supportingContent = {
                    Text(text = currentThemeData)
                },
                onClick = {
                    moreScreenViewModel.hideOrShowThemeDialog()
                }
            )
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.AutoAwesome, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "Dynamic theme")
                },
                supportingContent = {
                    Text(text = if (dynamicColorState) "Enabled" else "Disabled")
                },
                onClick = {
                    moreScreenViewModel.hideOrShowDynamicThemeBottomSheet()
                }
            )
        }

        if (uiState.showThemeDialog)
            ThemeSelectionDialog(
                currentTheme = currentThemeData,
                onDismissRequest = { newThemeData ->
                    moreScreenViewModel.hideOrShowThemeDialog()
                    moreScreenViewModel.updateThemeData(newThemeData)
                })

        if (uiState.showDynamicThemeBottomSheet)
            DynamicThemeBottomSheet(
                currentState = dynamicColorState,
                onDismissRequest = { boolean ->
                    moreScreenViewModel.updateDynamicTheme(boolean)
                    moreScreenViewModel.hideOrShowDynamicThemeBottomSheet()
                }
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun MoreScreenPreview() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    MoreScreen(
        scrollBehavior = scrollBehavior,
        moreScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
        mainNavController = rememberNavController()
    )
}