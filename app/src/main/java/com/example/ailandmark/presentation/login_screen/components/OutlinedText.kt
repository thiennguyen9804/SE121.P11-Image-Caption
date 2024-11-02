package com.example.ailandmark.presentation.login_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 80.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    style: TextStyle = TextStyle(),
    outlineColor: Color = Color.White,
) {

    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = outlineColor,
        style = TextStyle(
            drawStyle = Stroke(
                miter = 10f,
                width = 5f,
                join = StrokeJoin.Round
            )
        ),
    )
    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        fontWeight = fontWeight,
        style = style
    )
}