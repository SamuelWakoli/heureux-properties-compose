package com.heureux.properties.ui.presentation.main.sell_with_us_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellWithUsScreen(
    navController: NavController,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate back"
                    )
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Sell, contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Sell with us")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Column(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                Column(
                    Modifier
                        .widthIn(min = 400.dp, max = 600.dp)
                ) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "The information you are about to provide below will be analysed by our agents and more inspections will be done.")
                    Spacer(modifier = Modifier.size(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = "Property details",
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = {
                            Text(text = "Name")
                        },
                        supportingText = /*use null if no error*/ {
                            Text(text = "name cannot be empty")
                        },
                        isError = false,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        ),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium,
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Description")
                        },
                        supportingText = /*use null if no error*/ {
                            Text(text = "Description cannot be empty")
                        },
                        isError = false,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        ),
                        minLines = 3,
                        maxLines = 6,
                        shape = MaterialTheme.shapes.medium,
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = {
                            Text(text = "Price in Ksh.")
                        },
                        supportingText = /*use null if no error*/ {
                            Text(text = "Amount cannot be empty")
                        },
                        isError = false,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Upload images")
                            Spacer(modifier = Modifier.size(8.dp))
                            Icon(
                                imageVector = Icons.Default.Add, contentDescription = null
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = "Contact",
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = {
                            Text(text = "Phone number")
                        },
                        supportingText = /*use null if no error*/ {
                            Text(text = "Phone number cannot be empty")
                        },
                        isError = false,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium,
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                }
            }

            ElevatedButton(
                modifier = Modifier.padding(8.dp),
                onClick = { /*TODO*/ }) {
                Row(
                    modifier = Modifier.widthIn(min = 300.dp, max = 400.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Submit", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(
                        imageVector = Icons.Default.DoneAll, contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SellWithUsScreenPreview() {
    SellWithUsScreen(
        navController = rememberNavController()
    )
}