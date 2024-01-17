package com.heureux.properties.ui.presentation.screens.property_details_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.properties.ui.presentation.navigation.Screens
import com.heureux.properties.ui.presentation.screens.main_screen.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailsScreen(
    navController: NavController,
    viewModel: MainScreenViewModel,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.mainScreenUiState.collectAsState().value

    //used to check if it's necessary to have the inquiry button
    val userListings = viewModel.propertiesList.collectAsState().value?.filter {
        it.sellerId == viewModel.userProfileData.collectAsState().value?.userEmail
    }

    val userPurchasedProperties =
        viewModel.propertiesList.collectAsState().value?.filter { it.purchasedBy == viewModel.userProfileData.collectAsState().value?.userEmail }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(scrollBehavior = scrollBehavior, navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate back"
                )
            }
        }, title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info, contentDescription = null
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "Details")
            }
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary
        )
        )
    }) { paddingValues ->
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
                    if (uiState.currentProperty?.imageUrls != null) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                        ) {

                            items(uiState.currentProperty.imageUrls) { imageUrl ->
                                DetailsImageListItem(
                                    imageUrl = imageUrl,
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "No images available",
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally)
                        )
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



                if ((userListings != null && !userListings.contains(uiState.currentProperty!!)) &&
                    (userPurchasedProperties != null && !userPurchasedProperties.contains(uiState.currentProperty))
                ) {
                    ElevatedButton(
                        onClick = {
                            navController.navigate(Screens.InquiryScreen.route) {
                                launchSingleTop = true
                            }
                        }, modifier = Modifier
                            .widthIn(max = 400.dp)
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cases, contentDescription = null
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(text = "Inquire", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }
}






