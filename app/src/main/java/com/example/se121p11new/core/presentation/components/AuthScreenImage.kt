package com.example.se121p11new.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.R
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun AuthScreenImage(modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(1.0f)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(R.drawable.img),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        OutlinedText(
            text = "Image AI".toUpperCase(Locale.current),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (70).dp),
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            outlineColor = Color.White,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xff651A93),
                        Color(0xffEA80FC),
                    )
                )
            )
        )
    }
}

@Preview
@Composable
private fun AuthScreenImagePreview() {
    SE121P11NewTheme {
        AuthScreenImage()
    }
}