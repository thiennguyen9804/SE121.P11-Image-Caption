package com.example.se121p11new.presentation.forget_password_code_enter_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.components.AuthScreenImage
import com.example.se121p11new.core.presentation.components.SignInWithButton
import com.example.se121p11new.presentation.forget_password_code_enter_screen.components.OTPTextField
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun ForgetPasswordCodeEnter(modifier: Modifier = Modifier) {
    var otpValue by remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AuthScreenImage()
        Column(
            modifier = Modifier
                .height(490.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(
                    RoundedCornerShape(
                        topStart = 25.dp,
                        topEnd = 25.dp,
                    )
                )
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    modifier = Modifier
                        .offset(0.dp, 0.dp)
                        .clip(CircleShape)
                        .background(Color(0xffd9d9d9)),
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.enter_otp_text),
                color = Color(0xff651A93),
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            OTPTextField(
                otpValue = otpValue
            ) {
                if(otpValue.length <= 6) {
                    otpValue = it
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.code_havent_sent_yet_text),
                    color = Color.Black,
                )
                Text(
                    text = " " + stringResource(R.string.resend_text),
                    color = Color(0xffEA1616),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {

                        }
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff616AE5)
                ),
//                modifier = Modifier.padding(horizontal = 50.dp)
            ) {
                Text(
                    text = stringResource(R.string.confirm_text).toUpperCase(Locale.current),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.or_sign_in_with_text),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                SignInWithButton(
                    onClick = {},
                    resId = R.drawable.ic_facebook
                )
                SignInWithButton(
                    onClick = {},
                    resId = R.drawable.ic_x
                )
                SignInWithButton(
                    onClick = {},
                    resId = R.drawable.ic_google
                )
                SignInWithButton(
                    onClick = {},
                    resId = R.drawable.ic_guest
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.dont_have_account_text),
                modifier = Modifier
                    .clickable { }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = {  },
                border = BorderStroke(1.dp, color = Color(0xff616AE5)),
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                Text(
                    text = stringResource(R.string.sign_up_text).toUpperCase(Locale.current),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff616AE5),
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }

        }
    }
}

@Preview
@Composable
private fun ForgetPasswordCodeEnterPreview() {
    SE121P11NewTheme {
        ForgetPasswordCodeEnter()
    }
}
