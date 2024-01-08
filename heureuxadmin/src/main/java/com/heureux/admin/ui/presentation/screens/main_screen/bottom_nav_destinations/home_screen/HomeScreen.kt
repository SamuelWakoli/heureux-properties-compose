package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.heureux.admin.ui.AppViewModelProvider
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreenViewModel

@Composable
fun HomeScreen(
    bottomNavHostController: NavHostController,
    mainNavHostController: NavController,
    viewModel: MainScreenViewModel,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Home")
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        bottomNavHostController = rememberNavController(),
        mainNavHostController = rememberNavController(),
        viewModel = viewModel(factory = AppViewModelProvider.Factory)
    )
}