package com.example.se121p11new.presentation.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.core.presentation.components.CircularAvatar
import com.example.se121p11new.presentation.auth_group_screen.UserData
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit,
    userData: UserData = UserData("", "", ""),
    onUploadToCloud: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
    ) {
        IconButton(
            onClick = {
                onLogoutClick()
            },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = "Logout",
                tint = Color(0xFF7E57C2) // Màu tím
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp) // Đẩy nội dung xuống dưới nút đăng xuất
        ) {
            CircularAvatar(
                modifier = Modifier.size(150.dp),
                avatarUrl = userData.profilePictureUrl ?: "",
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Thông tin tài khoản
            Text(
                text = "Thông tin tài khoản:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Tên người dùng: ${userData.username}", color = Color.Black, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Email:", color = Color.Black, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))
            // Thao tác tài khoản
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
            Text(
                text = "Thao tác dữ liệu:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onUploadToCloud,
                ) {
                    Text(text = "Tải lên cloud")
                }
                Button(
                    onClick = { /* TODO: Đồng bộ dữ liệu */ },
                ) {
                    Text(text = "Tải về máy")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(thickness = 1.dp, color = Color.Gray)

            // Cài đặt
            Text(
                text = "Cài đặt:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview(modifier: Modifier = Modifier) {
    SE121P11NewTheme {
        ProfileScreen(
            onLogoutClick = {},
            onUploadToCloud = {}
        )
    }
}