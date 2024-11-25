package com.example.se121p11new.core.presentation.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest

@Composable
fun LocalAsyncImage(
    uriString: String,
    modifier: Modifier = Modifier,
//    @DrawableRes placeholder: Int? = null
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uriString)
//            .placeholder(placeholder)
            .build(),
        loading = { CircularProgressIndicator() },
        contentDescription = null,
        modifier = modifier
    )
}