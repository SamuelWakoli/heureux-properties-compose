package com.heureux.admin.ui.presentation.screens.profile_screen

import android.widget.Toast
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
import androidx.compose.material.icons.outlined.Logout
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.admin.ui.AppViewModelProvider
import com.heureux.admin.ui.presentation.composables.dialogs.SignOutDialog
import com.heureux.admin.ui.presentation.composables.images.CoilImage
import com.heureux.admin.ui.presentation.navigation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileScreenViewModel: ProfileScreenViewModel,
) {

    val uiState = profileScreenViewModel.uiState.collectAsState().value
    val userData = profileScreenViewModel.userProfileData.collectAsState().value

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
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
                    headlineContent = {
                        Text(text = userData?.displayName ?: "")
                    },
                    supportingContent = {
                        Text(text = userData?.userEmail ?: "")
                    },
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
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = null,
                        )
                    },
                    headlineContent = {
                        Text(text = "Sign out")
                    },
                    modifier = Modifier.clickable {
                        profileScreenViewModel.hideOrShowSignOutDialog()
                    }
                )

                if (uiState.showSignOutDialog) SignOutDialog(
                    onConfirmation = {
                        coroutineScope.launch {
                            profileScreenViewModel.signOut(
                                onSuccess = {
                                    navController.navigate(route = Screens.SignInScreen.route) {
                                        popUpTo(Screens.HomeScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onFailure = {
                                    profileScreenViewModel.hideOrShowSignOutDialog()
                                    Toast.makeText(
                                        context,
                                        "Failed to sign out, Please try again later",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    }, onDismissRequest = {
                        profileScreenViewModel.hideOrShowSignOutDialog()
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
        navController = rememberNavController(),
        profileScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
    )
}