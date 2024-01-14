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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.admin.ui.presentation.composables.property_list_item.PropertyListItem
import com.heureux.admin.ui.presentation.navigation.Screens
import com.heureux.admin.ui.presentation.screens.main_screen.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainScreenViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
) {

    val context = LocalContext.current
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO: Reset current property to null*/
                navController.navigate(Screens.AddPropertyScreen.route) {
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
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(20) {
                    PropertyListItem(
                        navController = navController,
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