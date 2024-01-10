package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.home_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.heureux.admin.ui.AppViewModelProvider
import com.heureux.admin.ui.presentation.composables.property_list_item.PropertyListItem
import com.heureux.admin.ui.presentation.navigation.Screens
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreenViewModel

@Composable
fun HomeScreen(
    bottomNavHostController: NavHostController,
    mainNavHostController: NavController,
    viewModel: MainScreenViewModel,
) {

    val context = LocalContext.current
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO: Reset current property to null*/
                mainNavHostController.navigate(Screens.AddPropertyScreen.route) {
                    launchSingleTop = true
                }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add property")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(20) {
                    PropertyListItem(
                        navController = mainNavHostController,
                        onUpdateCurrentProperty = { /*TODO*/ },
                        onClickDelete = { showDeleteDialog = true },
                    )
                }
                item {
                    Spacer(modifier = Modifier.size(64.dp))
                }
            }
        }

        if (showDeleteDialog) {
            DeletePropertyDialog(onDismissRequest = { showDeleteDialog = false }) {
                Toast.makeText(context, "Property deleting...", Toast.LENGTH_SHORT).show()
                //TODO:
            }
        }
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