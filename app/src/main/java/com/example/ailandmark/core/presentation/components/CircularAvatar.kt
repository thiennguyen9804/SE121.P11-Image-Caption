package com.example.ailandmark.core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ailandmark.R
import com.example.ailandmark.ui.theme.AILandmarkTheme

@Composable
fun CircularAvatar(
    modifier: Modifier = Modifier,
    avatarUrl: String = "",
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(2.dp, Color.Black, CircleShape)
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            placeholder = painterResource(R.drawable.kiana_placeholder)
        )
    }
}

@Preview
@Composable
private fun CircularAvatarPreview() {
    AILandmarkTheme {
        CircularAvatar(modifier = Modifier.size(60.dp))
    }
}