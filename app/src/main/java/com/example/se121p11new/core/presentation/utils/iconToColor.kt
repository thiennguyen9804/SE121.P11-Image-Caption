package com.example.se121p11new.core.presentation.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.se121p11new.R


// chọn màu nền dựa trên icon
fun iconToColor(@DrawableRes resId: Int): Color {
    val color = when(resId) {
        R.drawable.ic_facebook -> Color(0xff4F43D3)
        R.drawable.ic_x -> Color(0xff151515)
        R.drawable.ic_google -> Color(0xffD34343)
        R.drawable.ic_guest -> Color(0xffE4935D)
        else -> Color.Black
    }

    return color
}