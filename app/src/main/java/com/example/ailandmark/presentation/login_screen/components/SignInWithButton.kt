package com.example.ailandmark.presentation.login_screen.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ailandmark.core.presentation.utils.iconToColor

@Composable
fun SignInWithButton(
    onClick: () -> Unit,
    @DrawableRes resId: Int
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = iconToColor(resId)
        ),
        modifier = Modifier.size(65.dp)
    ) {
        Icon(
            painter = painterResource(resId),
            contentDescription = null,
        )
    }
}