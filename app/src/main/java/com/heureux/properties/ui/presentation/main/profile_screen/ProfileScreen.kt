package com.heureux.properties.ui.presentation.main.profile_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.presentation.composables.images.CoilImage
import com.heureux.properties.ui.presentation.navigation.Screens
import com.heureux.properties.ui.presentation.viewmodels.AppViewModelProvider
import com.heureux.properties.ui.presentation.viewmodels.MainScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    mainNavController: NavController,
    mainScreenViewModel: MainScreenViewModel,
) {

    val uiState = mainScreenViewModel.profileScreenUiState.collectAsState().value
    val userData = mainScreenViewModel.userData.collectAsState().value

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = uiState.currentUser) {
        if (uiState.currentUser == null) {
            mainNavController.navigate(route = Screens.SignInScreen.route) {
                launchSingleTop = true
                popUpTo(route = Screens.HomeScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { mainNavController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back",
                        )
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Profile image",
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Profile")
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(paddingValues)
                    .widthIn(min = 400.dp, max = 600.dp),
            ) {
                Text(
                    text = "My details",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                ListItem(
                    leadingContent = {
                        if (userData?.photoUrl != null && userData.photoUrl != "null") {
                            CoilImage(
                                modifier = Modifier.size(64.dp),
                                imageUrl = userData.photoUrl,
                                applyCircleShape = true
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = "Profile image",
                                modifier = Modifier.size(64.dp),
                            )
                        }
                    },
                    headlineContent = {
                        Text(text = userData?.name ?: "")
                    },
                    supportingContent = {
                        Text(text = userData?.email!!)
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
                        Text(text = userData?.phone ?: "Click to add phone number")
                    },
                    modifier = Modifier.clickable {
                        mainNavController.navigate(route = Screens.EditProfileScreen.route) {
                            launchSingleTop = true
                        }
                    }
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
                        mainNavController.navigate(route = Screens.MyPropertiesScreen.route) {
                            launchSingleTop = true
                        }
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
                        mainNavController.navigate(route = Screens.SoldPropertiesScreen.route) {
                            launchSingleTop = true
                        }
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
                        mainNavController.navigate(route = Screens.PaymentHistoryScreen.route) {
                            launchSingleTop = true
                        }
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
                        mainNavController.navigate(route = Screens.EditProfileScreen.route) {
                            launchSingleTop = true
                        }
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
                        mainScreenViewModel.showOrHideSignOutDialog(true)
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
                        mainScreenViewModel.showOrHideDeleteUserDataDialog(true)
                    }
                )

                if (uiState.showSignOutDialog) SignOutDialog(
                    onConfirmation = {
                        coroutineScope.launch {
                            mainScreenViewModel.signOut()
                        }
                    }, onDismissRequest = {
                        mainScreenViewModel.showOrHideSignOutDialog(false)
                    }
                )

                if (uiState.showDeleteProfileDialog) DeleteProfileDialog(
                    onConfirmation = {
                        coroutineScope.launch {
                            mainScreenViewModel.deleteUserAndData()
                        }
                    }, onDismissRequest = {
                        mainScreenViewModel.showOrHideDeleteUserDataDialog(false)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        mainNavController = rememberNavController(),
        mainScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
    )
}