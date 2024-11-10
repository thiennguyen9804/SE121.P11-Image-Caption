package com.example.se121p11new.presentation.camera_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun CaptureButton(
    modifier: Modifier = Modifier,
    spacing: Dp = 8.dp,
    border: Dp = 1.dp,
    onClick: () -> Unit = {}
) {
//    Box(
//        modifier = modifier
//            .clip(CircleShape)
//            .background(Color.Transparent)
//            .size(70.dp)
//            .border(2.dp, Color(0xff9A00F7), CircleShape)
//            .clickable(onClick = onClick)
//    ) {
//
//    }
    Box(
        modifier = modifier
            .border(border, Color(0xff9A00F7), CircleShape)
            .padding(spacing)
            .clip(CircleShape)
            .background(Color.White)
            .clickable(onClick = onClick)
    )
}

@Preview
@Composable
private fun CaptureButtonPreview() {
    SE121P11NewTheme {
        CaptureButton(modifier = Modifier.size(70.dp))
    }
}