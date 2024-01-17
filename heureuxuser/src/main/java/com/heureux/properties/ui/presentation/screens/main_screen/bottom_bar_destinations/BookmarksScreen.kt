package com.heureux.properties.ui.presentation.screens.main_screen.bottom_bar_destinations

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.AppViewModelProvider
import com.heureux.properties.ui.presentation.composables.property_list_item.PropertyListItem
import com.heureux.properties.ui.presentation.screens.main_screen.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: MainScreenViewModel,
) {

    val propertiesList = viewModel.propertiesList.collectAsState().value
    val bookmarksList = viewModel.bookmarksList.collectAsState().value
    val context = LocalContext.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (bookmarksList == null) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                trackColor = MaterialTheme.colorScheme.primaryContainer,
                strokeWidth = 2.dp
            )
        } else if (bookmarksList.isEmpty()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Bookmarks,
                    contentDescription = null,
                    modifier = Modifier.size(128.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "No Bookmarks Added",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        } else {
            LazyColumn(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(bookmarksList) { property ->
                    val propertyInMainCollection = propertiesList?.find {
                        it.id == property.id
                    }
                    PropertyListItem(
                        isSold = (propertyInMainCollection?.purchasedBy != "null"),
                        property = property,
                        navController = navController,
                        onUpdateCurrentProperty = { viewModel.updateCurrentProperty(property) },
                        isBookmarked = bookmarksList.contains(property) ?: false,
                        onClickBookmark = {
                            viewModel.updateBookmark(property) { exception: Exception ->
                                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BookmarksScreenPreview() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    BookmarksScreen(
        navController = rememberNavController(),
        scrollBehavior = scrollBehavior,
        viewModel = viewModel(factory = AppViewModelProvider.Factory),
    )
}