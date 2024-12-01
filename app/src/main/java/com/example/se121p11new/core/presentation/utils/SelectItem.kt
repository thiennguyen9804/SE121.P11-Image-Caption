package com.example.se121p11new.core.presentation.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class SelectItem(
    val title: String,
    @DrawableRes val icon: Int,
    val onClick: () -> Unit,
    val tint: Color
)