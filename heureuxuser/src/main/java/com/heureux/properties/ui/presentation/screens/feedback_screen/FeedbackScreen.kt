package com.heureux.properties.ui.presentation.screens.feedback_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.heureux.properties.data.types.FeedbackItem
import com.heureux.properties.ui.presentation.screens.main_screen.MainScreenViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel,
) {

    var feedbackText by rememberSaveable {
        mutableStateOf("")
    }

    var showFeedbackError by rememberSaveable {
        mutableStateOf(false)
    }

    var isSending by rememberSaveable {
        mutableStateOf(false)
    }

    val focusRequester = FocusRequester()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val userEmail = viewModel.userProfileData.collectAsState().value?.userEmail
    val context = LocalContext.current

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
                        Text(text = "Feedback")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = feedbackText,
                onValueChange = { value ->
                    showFeedbackError = false
                    feedbackText = value
                },
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .weight(1f)
                    .focusRequester(focusRequester),
                placeholder = {
                    Text(text = "Type here")
                },
                supportingText = {
                    if (showFeedbackError) Text(text = "Cannot send empty message")
                },
                isError = showFeedbackError,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                ),
                singleLine = false,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                )
            )
            ElevatedButton(
                onClick = {
                    if (feedbackText.isEmpty()) {
                        showFeedbackError = true
                    } else {
                        isSending = true
                        viewModel.sendFeedback(
                            feedbackItem = FeedbackItem(
                                id = LocalDateTime.now().toString(),
                                message = feedbackText,
                                time = LocalDateTime.now().toString(),
                                senderEmail = userEmail ?: "anonymous",
                            ),
                            onSuccess = {
                                Toast.makeText(context, "Feedback sent", Toast.LENGTH_SHORT).show()
                                navController.navigateUp()
                            },
                            onFailure = { exception: Exception ->
                                isSending = false
                                Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp).widthIn(max = 600.dp)
            ) {
                Row(
                    modifier = Modifier.widthIn(min = 400.dp, max = 600.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Send",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                    )
                    if (isSending) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp,
                            trackColor = MaterialTheme.colorScheme.primaryContainer,
                            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
                        )
                    } else {
                        Icon(imageVector = Icons.Outlined.Send, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.size(8.dp))

                }
            }
        }
    }
}