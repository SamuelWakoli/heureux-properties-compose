package com.heureux.admin.ui.presentation.screens.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.heureux.admin.ui.AppViewModelProvider
import com.heureux.properties.ui.presentation.screens.main_screen.MainScreenBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel,
) {
    val bottomNavController: NavHostController = rememberNavController()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val userData = viewModel.userProfileData.collectAsState().value

    Scaffold(topBar = {
        MainScreenAppbar(
            userData = userData,
            scrollBehavior = scrollBehavior,
            bottomNavController = bottomNavController,
            mainNavController = navController,
        )
    }, bottomBar = {
        MainScreenBottomBar(
            bottomNavController = bottomNavController,
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

        }

    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MaterialTheme {
        MainScreen(
            navController = rememberNavController(),
            viewModel = viewModel(factory = AppViewModelProvider.Factory)
        )
    }
}