package com.heureux.admin.ui.presentation.screens.user_feedback_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.admin.data.types.FeedbackItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFeedbackScreen(navController: NavController) {


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
                        Icon(imageVector = Icons.Outlined.Feedback, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "User Feedback")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    ) { paddingValues: PaddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(3) {
                UserFeedbackListItem(
                    feedback = FeedbackItem(
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed quis sem vel nisl bibendum malesuada. Proin congue justo nec felis tincidunt, eu elementum elit commodo. Mauris in justo id quam congue tincidunt. Integer suscipit, felis vel gravida scelerisque, justo elit tincidunt nisl, eu fringilla leo elit vel justo. Sed ut justo nec elit ultricies fermentum. Vivamus nec scelerisque nisl. In hac habitasse platea dictumst. Integer eu dolor eu orci tincidunt hendrerit. Integer auctor libero ac risus tincidunt, at fringilla dolor suscipit. Nunc consequat eros ut est laoreet, a fermentum turpis dapibus. Suspendisse potenti. Sed feugiat elit ut lectus fermentum, ut tempus nunc consectetur.",
                        time = "12:30 pm",
                        senderEmail = "tester@gmail.com",

                        )
                )
            }
            item {
                ListItem(
                    modifier = Modifier.widthIn(max = 600.dp),
                    headlineContent = { Text(text = "Show read feedbacks") },
                    trailingContent = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    },
                )
            }
        }
    }
}