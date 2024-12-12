package com.example.se121p11new.core.presentation.utils

import android.annotation.SuppressLint
import android.app.Activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@SuppressLint("ComposableNaming")
fun setStatusBarColor(color: Color) {
    val view = LocalView.current
    if(!view.isInEditMode) {
        LaunchedEffect(key1 = Unit) {
            val window = (view.context as Activity).window
            window.statusBarColor = color.toArgb()

        }
    }
}