package com.example.se121p11new.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.utils.SelectItem
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.ImageFolder

@Composable
fun CreatedFolderItem(
    imageFolder: ImageFolder,
    onDeleteImageFolder: (ImageFolder) -> Unit,
    modifier: Modifier = Modifier,
    onGoToImageFolderDetail: (ImageFolder) -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val items = listOf(
        SelectItem(
            title = "View",
            icon = R.drawable.ic_view,
            onClick = {
                onGoToImageFolderDetail(imageFolder)
            },
            tint = Color(0xff616AE5)
        ),

        SelectItem(
            title = "Delete",
            icon = R.drawable.ic_delete,
            onClick = {
                onDeleteImageFolder(imageFolder)
            },
            tint = Color(0xffEA1616)
        ),
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onGoToImageFolderDetail(imageFolder)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Folder,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = imageFolder.name,
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

//                DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false },
//                    containerColor = Color.White,
//                ) {
//                    items.forEach { item ->
//                        DropdownMenuItem(
//                            modifier = Modifier
//                                .height(35.dp),
//                            leadingIcon = {
//                                Icon(
//                                    painter = painterResource(item.icon),
//                                    contentDescription = null,
//                                    tint = item.tint
//                                )
//                            },
//                            text = {
//                                Text(text = item.title, color = Color.Black)
//                            },
//                            onClick = {
//                                expanded = false
//                                item.onClick()
//                            }
//                        )
//                        if(item != items.last()) {
//                            HorizontalDivider(
//                                modifier = Modifier
//                                    .padding(horizontal = 2.dp)
//                                    .height(1.dp)
//                                    .background(Color.Black)
//                            )
//                        }
//                    }
//                }
            PopUpMenu(items)
        }

    }
}