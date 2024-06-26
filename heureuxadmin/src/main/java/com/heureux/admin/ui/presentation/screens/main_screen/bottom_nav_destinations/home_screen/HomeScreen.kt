package com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.home_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.admin.ui.presentation.composables.property_list_item.PropertyListItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
) {

    val context = LocalContext.current

    val uiState = viewModel.uiState.collectAsState().value
    val allProperties = viewModel.propertiesList.collectAsState().value



    Column(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
    ) {

        if (allProperties == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                    strokeWidth = 2.dp
                )
            }
        } else if (allProperties.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Business,
                    contentDescription = null,
                    modifier = Modifier.size(128.dp),
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "No properties found",
                    style = MaterialTheme.typography.headlineSmall
                )

            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(allProperties) { property ->
                    PropertyListItem(
                        property = property,
                        navController = navController,
                        onUpdateCurrentProperty = { viewModel.updateCurrentPropertyState(property) },
                        onClickDelete = {
                            viewModel.updateCurrentPropertyState(property)
                            viewModel.updateDeleteDialogState()
                        },
                    )
                }
                item {
                    Spacer(modifier = Modifier.size(64.dp))
                }
            }
        }
    }

    if (uiState.showDeleteDialog) {
        DeletePropertyDialog(
            propertyName = uiState.currentProperty?.name.toString(),
            onDismissRequest = { viewModel.updateDeleteDialogState() }) {
            Toast.makeText(context, "Property deleting...", Toast.LENGTH_SHORT).show()
            viewModel.onDeletePropertyClicked(property = uiState.currentProperty!!)
        }
    }


}