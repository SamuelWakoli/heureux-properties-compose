package com.heureux.properties.ui.presentation.screens.sell_with_us_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.AppViewModelProvider

@Composable
fun AddPropertyImagesScreen(navController: NavHostController, viewModel: SellWithUsViewModel) {

}

@Preview
@Composable
private fun AddPropertyImagesScreenPreview() {
    AddPropertyImagesScreen(
        navController = rememberNavController(),
        viewModel = viewModel(factory = AppViewModelProvider.Factory)
    )
}