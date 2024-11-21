package com.example.se121p11new.presentation.auth_group_screen.forget_password_code_enter_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    otpValue: String,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        value = otpValue,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier,
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                repeat(6) { index ->
                    val char = when {
                        index >= otpValue.length -> ""
                        else -> otpValue[index].toString()
                    }
                    Text(
                        modifier = Modifier
                            .size(45.dp)
                            .border(2.dp, Color.Black, CircleShape)
                            .padding(4.dp),
                        text = char,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
//                        fontWeight = FontWeight.Bold

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

        }
    )
}

@Preview
@Composable
private fun OTPTextFieldPreview() {
    var otpValue = "123456"
    SE121P11NewTheme {
        OTPTextField(
            otpValue = otpValue
        ) {
            if(otpValue.length <= 6) {
                otpValue = it
            }
        }
    }
}