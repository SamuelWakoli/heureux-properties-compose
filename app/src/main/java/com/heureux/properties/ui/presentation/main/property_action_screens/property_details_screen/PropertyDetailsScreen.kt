package com.heureux.properties.ui.presentation.main.property_action_screens.property_details_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cases
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.AppViewModelProvider
import com.heureux.properties.ui.presentation.main.main_screen.MainScreenViewModel
import com.heureux.properties.ui.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailsScreen(
    navController: NavController,
    viewModel: MainScreenViewModel,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.mainScreenUiState.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = "Details")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            Column(
                Modifier
                    .fillMaxWidth()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Column(
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                    ) {
                        items(6) {
                            DetailsImageListItem(
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Column(
                        Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Price: Ksh. ${uiState.currentProperty?.price}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = uiState.currentProperty?.name ?: "",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Text(
                            text = uiState.currentProperty?.description ?: "",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }

                ElevatedButton(
                    onClick = {
                        navController.navigate(Screens.InquiryScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .widthIn(max = 400.dp)
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cases,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = "Inquire", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PropertyDetailsScreenPreview() {
    PropertyDetailsScreen(
        navController = rememberNavController(),
        viewModel = viewModel(factory = AppViewModelProvider.Factory)
    )
}