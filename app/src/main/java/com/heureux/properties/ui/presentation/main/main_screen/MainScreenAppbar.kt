package com.heureux.properties.ui.presentation.main.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.heureux.properties.R
import com.heureux.properties.data.HeureuxUser
import com.heureux.properties.ui.presentation.composables.images.CoilImage
import com.heureux.properties.ui.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenAppbar(
    bottomNavController: NavController,
    mainNavController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    userData: HeureuxUser?,
) {
    val bottomNavBackStackEntry = bottomNavController.currentBackStackEntryAsState()
    val currentRoute = bottomNavBackStackEntry.value?.destination?.route

    var showDropdownMenu by rememberSaveable {
        mutableStateOf(false)
    }

    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            // TODO: Insert user circle profile pic here
            IconButton(onClick = {
                mainNavController.navigate(route = Screens.ProfileScreen.route) {
                    launchSingleTop = true
                }
            }) {
                if (userData?.photoUrl != null && userData.photoUrl != "null") {
                    CoilImage(
                        modifier = Modifier.size(36.dp),
                        imageUrl = userData.photoUrl,
                        applyCircleShape = true,
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
                modifier = Modifier
                    .clickable {
                        mainNavController.navigate(route = Screens.AboutUsScreen.route) {
                            launchSingleTop = true
                        }
                    }
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_round),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Heureux Properties")
            }
        },
        actions = {
            IconButton(onClick = { showDropdownMenu = true }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Options")
                DropdownMenu(
                    expanded = showDropdownMenu,
                    onDismissRequest = { showDropdownMenu = false },
                    content = {
                        if (currentRoute == Screens.HomeScreen.route) {
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.FilterList,
                                        contentDescription = null,
                                    )
                                },
                                text = { Text(text = "Filter") },
                                onClick = {
                                    showDropdownMenu = false
                                    mainNavController.navigate(Screens.FilterScreen.route) {
                                        launchSingleTop = true
                                    }
                                },
                            )
                        }
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.SupportAgent,
                                    contentDescription = null
                                )
                            },
                            text = { Text(text = "Contact us") }, onClick = {
                                showDropdownMenu = false
                                mainNavController.navigate(Screens.ContactUsScreen.route) {
                                    launchSingleTop = true
                                }
                            })
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Feedback,
                                    contentDescription = null
                                )
                            },
                            text = { Text(text = "Feedback") }, onClick = {
                                showDropdownMenu = false
                                mainNavController.navigate(Screens.FeedbackScreen.route) {
                                    launchSingleTop = true
                                }
                            })

                    },
                )
            }
        }
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
            userData = HeureuxUser(
                photoUrl = null,
                name = "Sam",
                email = "swwakoli@gmail.com",
                phone = "0797228948",
                bookmarks = null,
                propertiesOwned = null,
                listings = null,
            ),
        )
    }
}