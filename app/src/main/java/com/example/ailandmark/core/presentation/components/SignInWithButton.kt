package com.example.ailandmark.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ailandmark.R
import com.example.ailandmark.core.presentation.utils.iconToColor
import com.example.ailandmark.ui.theme.AILandmarkTheme

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
        modifier = Modifier.width(70.dp)
    ) {
        Icon(
            painter = painterResource(resId),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun SignInWithButtonPreview() {
    AILandmarkTheme {
        SignInWithButton(
            onClick = { },
            resId = R.drawable.ic_facebook
        )
    }
}