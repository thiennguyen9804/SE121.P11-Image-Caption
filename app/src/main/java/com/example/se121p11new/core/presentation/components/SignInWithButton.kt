package com.example.se121p11new.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.utils.iconToColor
import com.example.se121p11new.ui.theme.SE121P11NewTheme

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
        modifier = Modifier.width(70.dp).height(35.dp)
    ) {
        Icon(
            painter = painterResource(resId),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Preview
@Composable
private fun SignInWithButtonPreview() {
    SE121P11NewTheme {
        SignInWithButton(
            onClick = { },
            resId = R.drawable.ic_facebook
        )
    }
}