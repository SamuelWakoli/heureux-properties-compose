package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.more_screen

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
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.admin.ui.presentation.composables.bottom_sheet.DynamicThemeBottomSheet
import com.heureux.admin.ui.presentation.composables.dialogs.ThemeSelectionDialog
import com.heureux.admin.ui.presentation.composables.more_screen_list_item.MoreScreenListItem
import com.heureux.admin.ui.presentation.navigation.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: MoreScreenViewModel,
    mainNavController: NavController,
) {

    val uiState = viewModel.uiState.collectAsState().value

    val currentThemeData =
        viewModel.currentThemeData.collectAsState(initial = "Light").value
    val dynamicColorState =
        viewModel.currentDynamicColorState.collectAsState(initial = false).value

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
//            MoreScreenListItem(
//                leadingIcon = {
//                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = null)
//                },
//                headlineContent = {
//                    Text(text = "Privacy policy (user app)")
//                }
//            )
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Feedback, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "User feedback")
                },
                onClick = {
                    mainNavController.navigate(Screens.UserFeedbackScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
            MoreScreenListItem(
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.RateReview, contentDescription = null)
                },
                headlineContent = {
                    Text(text = "PlayStore ratings")
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
                    viewModel.hideOrShowThemeDialog()
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
                    viewModel.hideOrShowDynamicThemeBottomSheet()
                }
            )
        }

        if (uiState.showThemeDialog)
            ThemeSelectionDialog(
                currentTheme = currentThemeData,
                onDismissRequest = { newThemeData ->
                    viewModel.hideOrShowThemeDialog()
                    viewModel.updateThemeData(newThemeData)
                })

        if (uiState.showDynamicThemeBottomSheet)
            DynamicThemeBottomSheet(
                currentState = dynamicColorState,
                onDismissRequest = { boolean ->
                    viewModel.updateDynamicTheme(boolean)
                    viewModel.hideOrShowDynamicThemeBottomSheet()
                }
            )
    }
}