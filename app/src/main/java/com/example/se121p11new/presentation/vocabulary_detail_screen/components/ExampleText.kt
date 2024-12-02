package com.example.se121p11new.presentation.vocabulary_detail_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.se121p11new.presentation.image_captioning_screen.components.ClickableWords

@Composable
fun ExampleText(
    example: String,
    modifier: Modifier = Modifier
) {
    Text(example, modifier = modifier)
}