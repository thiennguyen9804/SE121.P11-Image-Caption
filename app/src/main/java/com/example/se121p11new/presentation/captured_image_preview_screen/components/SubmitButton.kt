package com.example.se121p11new.presentation.captured_image_preview_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.R
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .border(1.dp, Color.Black, RoundedCornerShape(35.dp))
            .clip(RoundedCornerShape(35.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color(0xff9A00F7))
        )

        Text(text = stringResource(R.string.analyze_text), fontSize = 10.sp)
    }
}

@Preview
@Composable
private fun SubmitButtonPreview() {
    SE121P11NewTheme {
        SubmitButton(
            modifier = Modifier
                .width(90.dp)
                .height(35.dp),
            onClick = {}
        )
    }
}