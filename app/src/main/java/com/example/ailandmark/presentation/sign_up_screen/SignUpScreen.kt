package com.example.ailandmark.presentation.sign_up_screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ailandmark.R
import com.example.ailandmark.core.presentation.components.OutlinedText
import com.example.ailandmark.core.presentation.components.SignInWithButton
import com.example.ailandmark.ui.theme.AILandmarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navigateToLogin: () -> Unit,
    signUp: () -> Unit
) {
    var loginName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .scale(1.0f)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(R.drawable.img),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            OutlinedText(
                text = "Image AI".toUpperCase(Locale.current),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (70).dp),
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold,
                outlineColor = Color.White,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xff651A93),
                            Color(0xffEA80FC),
                        )
                    )
                )
            )
        }
        Column(
            modifier = Modifier
                .height(520.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(
                    topStart = 25.dp,
                    topEnd = 25.dp,
                ))
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 30.dp)
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.sign_up_text),
                color = Color(0xff651A93),
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.had_account_already_text),
                    color = Color.Black,
                )
                Text(
                    text = " " + stringResource(R.string.login_text) + "!",
                    color = Color(0xffEA1616),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            navigateToLogin()
                        }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = loginName,
                onValueChange = { loginName = it },
                modifier = Modifier.fillMaxWidth(),
                label = {Text(stringResource(R.string.sign_in_name_text))},
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = {Text(stringResource(R.string.password_text))},
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = passwordConfirm,
                onValueChange = { passwordConfirm = it },
                modifier = Modifier.fillMaxWidth(),
                label = {Text(stringResource(R.string.password_confirm_text))},
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(15.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(0.dp)
//            ) {
//                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
//                    Checkbox(
//                        checked = checked,
//                        onCheckedChange = {
//                            checked = it
//                        }
//                    )
//                }
//                Spacer(modifier = Modifier.width(5.dp))
//                Text(
//                    text = stringResource(R.string.remember_me_text),
//                    fontWeight = FontWeight.SemiBold
//                )
//            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = { signUp() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff616AE5)
                )
            ) {
                Text(
                    text = stringResource(R.string.sign_up_text).toUpperCase(Locale.current),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
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
        }
    }


}

@Preview
@Composable
private fun SignUpScreenPreview() {
    AILandmarkTheme {
        SignUpScreen(
            navigateToLogin = { },
            signUp = { },
        )
    }
}