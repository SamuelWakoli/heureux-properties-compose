package com.heureux.admin.ui.presentation.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.heureux.admin.R
import com.heureux.admin.data.types.UserProfileData
import com.heureux.admin.ui.presentation.composables.images.CoilImage
import com.heureux.admin.ui.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenAppbar(
    bottomNavController: NavController,
    mainNavController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    userData: UserProfileData?,
) {
    val bottomNavBackStackEntry = bottomNavController.currentBackStackEntryAsState()
    val currentRoute = bottomNavBackStackEntry.value?.destination?.route

    var showDropdownMenu by rememberSaveable {
        mutableStateOf(false)
    }


    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = {
                mainNavController.navigate(route = Screens.ProfileScreen.route) {
                    launchSingleTop = true
                }
            }) {
                if (userData?.photoURL != null && userData.photoURL.toString() != "null") {
                    CoilImage(
                        modifier = Modifier.size(36.dp),
                        imageUrl = userData.photoURL.toString(),
                        applyCircleShape = true,
                        errorContent = {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = "Profile",
                                modifier = Modifier.size(36.dp)
                            )
                        },
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "Profile",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_round),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Heureux Admin",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun MainScreenAppbarPreview() {
    MaterialTheme {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        MainScreenAppbar(
            bottomNavController = rememberNavController(),
            mainNavController = rememberNavController(),
            scrollBehavior = scrollBehavior,
            userData = UserProfileData(
                displayName = "Test",
                photoURL = "",
                userEmail = "test@gmail.com",
                phone = "1234567890",
            ),
        )
    }
}