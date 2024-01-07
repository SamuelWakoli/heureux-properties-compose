package com.heureux.properties.ui.presentation.screens.sell_with_us_screen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.AppViewModelProvider
import com.heureux.properties.ui.presentation.composables.images.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPropertyImagesScreen(navController: NavHostController, viewModel: SellWithUsViewModel) {

    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value
    var profileImageUri: Uri? by remember { mutableStateOf(null) }
    var profileBitmap: Bitmap? by remember { mutableStateOf(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                profileImageUri = uri

                Toast.makeText(context, "Image uploading, please wait...", Toast.LENGTH_LONG).show()
                viewModel.uploadProfileImage(
                    uri = uri,
                    onSuccess = { photoURL ->
                        viewModel.updatePhotoURLAndCount(photoURL)
                    },
                    onFailure = { exception ->
                        Toast.makeText(
                            context,
                            "An error occurred: [${exception.message}] Please try editing profile image later",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                title = {
                    Text(text = "Upload Images")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Column {



                profileImageUri?.let { uri ->
                    profileBitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(
                            context.contentResolver,
                            uri
                        )
                    } else {
                        val source =
                            ImageDecoder.createSource(context.contentResolver, uri)
                        ImageDecoder.decodeBitmap(source)
                    }

                    if (profileBitmap != null)
                        Image(
                            bitmap = profileBitmap?.asImageBitmap()!!,
                            contentDescription = "New profile image",
                            modifier = Modifier.size(518.dp),
                            contentScale = ContentScale.Crop,
                        )
                    Spacer(modifier = Modifier.size(16.dp))
                }

                Spacer(modifier = Modifier.size(16.dp))
                if (uiState.propertiesImagesCount != 0) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Selected images:")
                    LazyRow(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(uiState.propertyImages) { imageUrl ->
                            CoilImage(
                                imageUrl = imageUrl,
                                modifier = Modifier
                                    .height(180.dp)
                                    .width(120.dp)
                                    .padding(horizontal = 2.dp)
                            )
                        }
                        item {
                            TextButton(onClick = { viewModel.clearImageSelection() }) {
                                Text(text = "Clear")
                                Spacer(modifier = Modifier.size(4.dp))
                                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedButton(onClick = { launcher.launch("image/*") }) {
                    Text(text = if (uiState.propertiesImagesCount == 0) "Select Image" else "Select another image")
                }
                Spacer(modifier = Modifier.size(8.dp))
                if (uiState.propertiesImagesCount != 0) {
                    ElevatedButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier
                            .widthIn(min = 400.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Done")
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                }

            }

        }
    }
}

@Preview
@Composable
private fun AddPropertyImagesScreenPreview() {
    AddPropertyImagesScreen(
        navController = rememberNavController(),
        viewModel = viewModel(factory = AppViewModelProvider.Factory)
    )
}