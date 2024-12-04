package com.example.se121p11new.core.presentation.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import io.realm.kotlin.types.RealmObject

data class SelectItem(
    val title: String,
    @DrawableRes val icon: Int,
    var onClick: () -> Unit,
    val tint: Color
) {
}