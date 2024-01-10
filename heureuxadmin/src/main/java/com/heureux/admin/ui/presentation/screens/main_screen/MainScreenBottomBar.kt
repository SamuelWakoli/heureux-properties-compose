package com.heureux.admin.ui.presentation.screens.main_screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Cases
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Cases
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.More
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.heureux.admin.ui.presentation.navigation.Screens

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
        selectedIcon = Icons.Default.Business,
        unselectedIcon = Icons.Outlined.Business,
    ),
    MainBottomBarItem(
        label = "Users",
        route = Screens.UsersScreen.route,
        selectedIcon = Icons.Default.Group,
        unselectedIcon = Icons.Outlined.Group,
    ),
    MainBottomBarItem(
        label = "Inquiries",
        route = Screens.InquiriesScreen.route,
        selectedIcon = Icons.Default.Cases,
        unselectedIcon = Icons.Outlined.Cases,
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
    BottomAppBar{
        val backStackEntry = bottomNavController.currentBackStackEntryAsState()

        mainBottomBarItems.forEach { homeBottomBarItem: MainBottomBarItem ->
            val currentRoute = backStackEntry.value?.destination?.route;
            val selected = currentRoute == homeBottomBarItem.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    bottomNavController.navigate(homeBottomBarItem.route) {
                        launchSingleTop = true
                        popUpTo(route = Screens.HomeScreen.route) {
                            inclusive = false
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun MainScreenBottomBarPreview() {
    MaterialTheme {
        MainScreenBottomBar(
            bottomNavController = rememberNavController(),
        )
    }
}