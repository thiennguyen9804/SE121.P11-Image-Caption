package com.example.se121p11new.presentation.dashboard_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun SavedVocabularyItem() {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xffd9d9d9))
            .fillMaxWidth()
            .padding(start = 10.dp, end = 5.dp),
        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text("Expressive", color = Color(0xff9A00F7))
            Text(" : Ấn tượng")
        }

        IconButton(
            onClick = {

            },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowRightAlt,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun SavedVocabularyItemPreview() {
    SE121P11NewTheme {
        SavedVocabularyItem()
    }
}