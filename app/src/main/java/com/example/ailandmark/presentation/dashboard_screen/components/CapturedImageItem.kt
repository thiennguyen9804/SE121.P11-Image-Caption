package com.example.ailandmark.presentation.dashboard_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ailandmark.R
import com.example.ailandmark.ui.theme.AILandmarkTheme

@Composable
fun CapturedImageItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            placeholder = painterResource(R.drawable.kiana_placeholder_2),
            model = null,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Image 1",
            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun CapturedImageItemPreview() {
    AILandmarkTheme {
        CapturedImageItem()
    }
}