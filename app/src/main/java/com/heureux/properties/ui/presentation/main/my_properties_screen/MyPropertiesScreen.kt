package com.heureux.properties.ui.presentation.main.my_properties_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MyPropertiesScreen(
    mainNavController: NavController,
) {

}

@Preview
@Composable
private fun MyPropertiesScreenPreview() {
    MyPropertiesScreen(
        mainNavController = rememberNavController()
    )
}