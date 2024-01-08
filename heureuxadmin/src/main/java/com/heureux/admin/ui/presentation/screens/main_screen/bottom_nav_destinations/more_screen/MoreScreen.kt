package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.more_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MoreScreen(
    bottomNavHostController: NavHostController,
    mainNavHostController: NavController,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "More")
    }
}

@Preview
@Composable
private fun MoreScreenPreview() {
    MoreScreen(
        bottomNavHostController = rememberNavController(),
        mainNavHostController = rememberNavController(),
    )
}