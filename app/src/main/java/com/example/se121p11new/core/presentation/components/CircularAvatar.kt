package com.example.se121p11new.core.presentation.components

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
import coil3.compose.AsyncImage
import com.example.se121p11new.R
import com.example.se121p11new.ui.theme.SE121P11NewTheme


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
    SE121P11NewTheme {
        CircularAvatar(modifier = Modifier.size(60.dp))
    }
}