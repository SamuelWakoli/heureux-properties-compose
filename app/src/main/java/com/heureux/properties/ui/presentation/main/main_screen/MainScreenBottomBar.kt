package com.heureux.properties.ui.presentation.main.main_screen

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.More
import androidx.compose.material.icons.outlined.ViewList
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.presentation.navigation.Screens

data class MainBottomBarItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val mainBottomBarItems: List<MainBottomBarItem> = listOf(
    MainBottomBarItem(
        label = "Home",
        route = Screens.HomeScreen.route,
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    MainBottomBarItem(
        label = "Bookmarks",
        route = Screens.BookmarksScreen.route,
        selectedIcon = Icons.Default.Bookmarks,
        unselectedIcon = Icons.Outlined.Bookmarks,
    ),
    MainBottomBarItem(
        label = "My Listings",
        route = Screens.MyListingsScreen.route,
        selectedIcon = Icons.Default.ViewList,
        unselectedIcon = Icons.Outlined.ViewList,
    ),
    MainBottomBarItem(
        label = "More",
        route = Screens.MoreScreen.route,
        selectedIcon = Icons.Default.More,
        unselectedIcon = Icons.Outlined.More,
    ),

    )

@Composable
fun MainScreenBottomBar(bottomNavController: NavHostController) {
    BottomAppBar {
        NavigationBar {
            val backStackEntry = bottomNavController.currentBackStackEntryAsState()

            mainBottomBarItems.forEach { homeBottomBarItem: MainBottomBarItem ->
                val currentRoute = backStackEntry.value?.destination?.route;
                val selected = currentRoute == homeBottomBarItem.route

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        Log.d("BOTTOM BAR", "Current route: $currentRoute")
                        bottomNavController.navigate(homeBottomBarItem.route) {
                            launchSingleTop = true
                            popUpTo(route = Screens.HomeScreen.route) {
                                inclusive = false
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (selected) homeBottomBarItem.selectedIcon else homeBottomBarItem.unselectedIcon,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = homeBottomBarItem.label) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenBottomBarPreview() {
    MaterialTheme { MainScreenBottomBar(bottomNavController = rememberNavController()) }
}