package com.example.ailandmark.presentation.description_screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap


@Composable
fun DescriptionScreen(
    bitmap: Bitmap?
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        bitmap?.asImageBitmap()?.let {
            Image(
                bitmap = it,
                modifier = Modifier.fillMaxSize(),
                contentDescription = null
            )
        }
    }
}