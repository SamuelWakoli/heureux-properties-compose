package com.heureux.admin.ui.presentation.screens.user_feedback_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.admin.ui.presentation.screens.main_screen.bottom_nav_destinations.more_screen.MoreScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFeedbackScreen(
    navController: NavController,
    viewModel: MoreScreenViewModel
) {

    val feedbacks = viewModel.feedbacks.collectAsState().value

    val feedbacksRead = feedbacks?.filter { it.isRead } ?: emptyList()

    val unreadFeedbacks = feedbacks?.filter { !it.isRead } ?: emptyList()

    var showReadFeedbacks by remember { mutableStateOf(false) }
    val rotationState = remember { mutableFloatStateOf(0f) }


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
        if (feedbacks == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (feedbacks.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Feedback,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "No feedbacks")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(unreadFeedbacks) { feedback ->
                    UserFeedbackListItem(
                        feedback = feedback,
                        onMarkAsRead = {
                            viewModel.updateFeedback(
                                feedbackItem = feedback.copy(isRead = true),
                                onSuccess = {},
                                onFailure = {},
                            )
                        },
                        onDelete = {
                            viewModel.deleteFeedback(
                                feedbackItem = feedback,
                                onSuccess = {},
                                onFailure = {},
                            )
                        }
                    )
                }
                item {
                    ListItem(
                        modifier = Modifier
                            .widthIn(max = 600.dp)
                            .clickable {
                                showReadFeedbacks = !showReadFeedbacks
                            },
                        headlineContent = { Text(text = if (showReadFeedbacks) "Hide read feedbacks" else "Show read feedbacks") },
                        trailingContent = {
                            IconButton(onClick = { showReadFeedbacks = !showReadFeedbacks }) {
                                if (showReadFeedbacks) {
                                    rotationState.floatValue = 180f
                                } else {
                                    rotationState.floatValue = 0f
                                }
                                Icon(
                                    imageVector = Icons.Outlined.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier.rotate(rotationState.floatValue)
                                )
                            }
                        },
                    )
                }
                if (showReadFeedbacks) {
                    items(feedbacksRead) { feedback ->
                        UserFeedbackListItem(
                            feedback = feedback,
                            onMarkAsRead = {
                            },
                            onDelete = {
                                viewModel.deleteFeedback(
                                    feedbackItem = feedback,
                                    onSuccess = {},
                                    onFailure = {},
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}