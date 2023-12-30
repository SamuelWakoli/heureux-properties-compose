package com.heureux.properties.ui.presentation.composables.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicThemeBottomSheet(
    currentState: Boolean,
    onDismissRequest: (Boolean) -> Unit,
) {

    ModalBottomSheet(onDismissRequest = {
        onDismissRequest(currentState)
    }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Dynamic Theme",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Tired of static theme? This app allows you to use your wallpaper colors, like magic! ✨\n" +
                        "This feature is only available on devices running on Android 12+",
                style = MaterialTheme.typography.bodyLarge
            )
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onDismissRequest(true)
                }) {
                Text(text = "Activate")
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onDismissRequest(false)
                }) {
                Text(text = "Deactivate")
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onDismissRequest(currentState) }) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.size(64.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DynamicThemeBottomSheetPreview() {
    DynamicThemeBottomSheet(
        currentState = false,
        onDismissRequest = {}
    )
}