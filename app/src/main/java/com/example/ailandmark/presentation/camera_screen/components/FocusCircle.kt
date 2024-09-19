package com.example.ailandmark.presentation.camera_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FocusCircle(
    x: Float,
    y: Float,
    visible: Boolean
) {
    AnimatedVisibility(visible = visible) {
        Canvas(modifier = Modifier.offset()) {
            drawCircle(color = Color.White, )
        }
    }
}
