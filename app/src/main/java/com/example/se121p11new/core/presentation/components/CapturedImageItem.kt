package com.example.se121p11new.core.presentation.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.ui.theme.SE121P11NewTheme

val items = listOf(
    SelectItem(
        title = "Delete",
        icon = R.drawable.ic_delete,
        onClick = {},
        tint = Color(0xffEA1616)
    ),

    SelectItem(
        title = "View",
        icon = R.drawable.ic_view,
        onClick = {},
        tint = Color(0xff616AE5)
    )
)

@Composable
fun CapturedImageItem(
    image: Image,
    onClick: (Image) -> Unit,
) {
    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }

    val bitmap by produceState<Resource<Bitmap>>(Resource.Loading(), image.pictureUri) {
        val tempBitmap = context.contentResolver.openInputStream(Uri.parse(image.pictureUri))
            .use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        value = if (tempBitmap == null) {
            Resource.Error("Image not found")
        } else {
            Resource.Success(tempBitmap)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 2.dp)
            .clickable {
                onClick(image)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when (bitmap) {
                is Resource.Error ->
                    Toast.makeText(
                        context,
                        bitmap.message,
                        Toast.LENGTH_SHORT
                    ).show()

                is Resource.Loading ->
                    CircularProgressIndicator()

                is Resource.Success ->
                    Image(
                        bitmap = bitmap.data!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.FillWidth
                    )
            }

            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = image.imageName,
                fontSize = 17.sp,
//            fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier
//                .fillMaxSize()
        ) {
            IconButton(
                onClick = {
                    expanded = true
                }
            ) {
                Icon(painter = painterResource(R.drawable.ic_kebab_menu), contentDescription = null)
            }

            MaterialTheme(
                shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(20.dp)))
            {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = Color.White,
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(item.icon),
                                    contentDescription = null,
                                    tint = item.tint
                                )
                            },
                            text = {
                                Text(text = item.title, color = Color.Black)
                            },
                            onClick = {
                                expanded = false
                            }
                        )
                    }
                }
            }

        }

    }
}

@Preview
@Composable
private fun CapturedImageItemPreview() {
    SE121P11NewTheme {
        CapturedImageItem(
            image = Image().apply {
                pictureUri = ""
                englishText = ""
                vietnameseText = ""
                imageName = "Image 1"
            },
            onClick = { }
        )
    }
}

data class SelectItem(
    val title: String,
    @DrawableRes val icon: Int,
    val onClick: () -> Unit,
    val tint: Color
)