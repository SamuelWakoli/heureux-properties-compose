package com.heureux.properties.ui.presentation.main.payment_history_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PaymentHistoryScreen(
    mainNavController: NavController,
) {

}

@Preview
@Composable
private fun PaymentHistoryScreenPreview() {
    PaymentHistoryScreen(
        mainNavController = rememberNavController()
    )
}