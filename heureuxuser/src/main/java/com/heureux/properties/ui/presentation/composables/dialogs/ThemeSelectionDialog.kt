package com.heureux.properties.ui.presentation.composables.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ThemeSelectionDialog(
    onDismissRequest: (selectedTheme: String) -> Unit, // Save Theme here
    currentTheme: String = "light",
) {

    var currentThemeData by remember {
        mutableStateOf(currentTheme)
    }

    Dialog(onDismissRequest = { onDismissRequest(currentThemeData) }) {
        Card(
            colors = CardDefaults.cardColors(
                contentColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector =
                    if (isSystemInDarkTheme())
                        Icons.Outlined.LightMode
                    else Icons.Outlined.DarkMode,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(32.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.size(8.dp))
                RadioListItem(
                    currentTheme = currentThemeData,
                    label = "Light"
                ) { newTheme ->
                    currentThemeData = newTheme
                    onDismissRequest(newTheme)
                }
                RadioListItem(
                    currentTheme = currentThemeData,
                    label = "Dark"
                ) { newTheme ->
                    currentThemeData = newTheme
                    onDismissRequest(newTheme)
                }
                RadioListItem(
                    currentTheme = currentThemeData,
                    label = "System"
                ) { newTheme ->
                    currentThemeData = newTheme
                    onDismissRequest(newTheme)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ThemeSelectionDialogPreview() {
    ThemeSelectionDialog(
        onDismissRequest = {

        },
    )
}

@Composable
fun RadioListItem(
    currentTheme: String,
    label: String,
    onClick: (label: String) -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable { onClick(label) },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        headlineContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = currentTheme == label, onClick = { onClick(label) })
                Text(text = label)
            }
        })
}

@Preview(showBackground = true)
@Composable
private fun RadioListItemPreview() {
    RadioListItem(
        currentTheme = "Dark",
        label = "Dark",
        onClick = {}
    )
}