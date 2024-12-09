package com.example.se121p11new.presentation.image_folder_group_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.components.PopUpMenu
import com.example.se121p11new.core.presentation.utils.SelectItem
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.ImageFolder
import com.example.se121p11new.ui.theme.SE121P11NewTheme



@Composable
fun CapturedImageItem(
    image: Image, // hình ảnh để hiển thị
    onClick: (Image) -> Unit,
    onDeleteImage: (Image) -> Unit,
    folderList: List<ImageFolder>, // tất cả các thư mục trong ứng dụng
    onAddImageToFolder: (Image, ImageFolder) -> Unit,
    onRemoveImageOutOfFolder: (Image, ImageFolder) -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    val items = listOf(
        SelectItem(
            title = "View",
            icon = R.drawable.ic_view,
            onClick = {
                onClick(image)
            },
            tint = Color(0xff616AE5)
        ),

        SelectItem(
            title = "Delete",
            icon = R.drawable.ic_delete,
            onClick = {
                onDeleteImage(image)
            },
            tint = Color(0xffEA1616)
        ),

        SelectItem(
            title = "Lưu",
            icon = R.drawable.ic_save,
            onClick = {
                showBottomSheet = true
                expanded = false
            },
            tint = Color(0xff9A00F7)
        ),
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(image)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (showBottomSheet) {
            ImageFolderBottomSheet(
                folderList = folderList,
                imageFolderList = image.imageList,
                showBottomSheet = showBottomSheet,
                onDismiss = {
                    showBottomSheet = false
                },
                onAddImageToFolder = {
                    onAddImageToFolder(image, it)
                },
                onRemoveImageOutOfFolder = {
                    onRemoveImageOutOfFolder(image, it)
                },
                onCreateNewImageFolder = {},
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.pictureUri)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = image.imageName,
                fontSize = 17.sp,
            )
        }

        Box {
            IconButton(
                onClick = {
                    expanded = true
                }
            ) {
                Icon(painter = painterResource(R.drawable.ic_kebab_menu), contentDescription = null)
            }

            MaterialTheme(
                shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
            ) {
                PopUpMenu(
                    items = items,
                    onDismissPopUpMenuRequest = {
                        expanded = false
                    },
                    expanded = expanded
                )
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
            onClick = { },
            onDeleteImage = { },
            folderList = emptyList(),
            onAddImageToFolder = { _, _ -> },
            onRemoveImageOutOfFolder = { _, _ ->},
        )
    }
}
