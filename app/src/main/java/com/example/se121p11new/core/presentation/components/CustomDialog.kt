package com.example.se121p11new.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun CustomDialog(
    text: String,
    onFolderCreate: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var folderName by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .background(Color.White)
                .padding(20.dp)

        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = folderName,
                onValueChange = {
                    folderName = it
                },
                textStyle = TextStyle().copy(
                    fontSize = 15.sp,
                    color = Color(0xff9A00F7)
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff9A00F7),
                    focusedLabelColor = Color(0xff9A00F7)
                ),
                label = {
                    Text(
                        text = "Tên thư mục",
                        fontWeight = FontWeight.Bold,
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(text = "Hủy")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff9A00F7),
                        contentColor = Color.White
                    ),
                    onClick = {
                        onFolderCreate(folderName)
                    }
                ) {
                    Text(text = "Tạo")
                }
            }

        }
    }
}

@PreviewLightDark
@Composable
private fun CustomDialogPreview() {
    SE121P11NewTheme {
        CustomDialog(
            text = "Thư mục ảnh mới",
            onFolderCreate = {},
            onDismissRequest = {}
        )
    }

}