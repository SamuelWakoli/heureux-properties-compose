package com.heureux.properties.ui.presentation.composables.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    imageUrl: String = "https://picsum.photos/200",
    applyCircleShape: Boolean = false,
    errorContent: @Composable (() -> Unit)? = null,
    loadingContent: @Composable (() -> Unit)? = null,
    emptyContent: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        val painter = rememberAsyncImagePainter(
            model = if (applyCircleShape) {
                ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
                    .transformations(CircleCropTransformation()).build()
            } else {
                ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
                    .build()
            }
        )

        if (painter.state is AsyncImagePainter.State.Loading) {
            if (loadingContent == null) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                )
            } else {
                loadingContent()
            }
        }


        if (painter.state is AsyncImagePainter.State.Empty) {
            if (emptyContent == null) {
                // TODO: Handle empty
            } else {
                emptyContent()
            }
        }

        if (painter.state is AsyncImagePainter.State.Error) {
            if (errorContent == null) {
                Icon(
                    imageVector = Icons.Outlined.Error,
                    contentDescription = "Image URL has error",
                    modifier = modifier
                )
            } else {
                errorContent()
            }

        }

        if (painter.state is AsyncImagePainter.State.Success) {
            Image(
                painter = painter,
                contentDescription = "Avatar image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CoilImagePreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CoilImage(
            imageUrl = "https://picsum.photos/200",
            applyCircleShape = true,
        )
    }
}