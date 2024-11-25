package com.example.se121p11new.presentation.image_folder_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.components.CapturedImageItem
import com.example.se121p11new.core.presentation.components.CircularAvatar
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun ImageFolderScreen(
    modifier: Modifier = Modifier,
    onChangeFolder: () -> Unit,
    onImageClick: (Image) -> Unit,
    images: List<Image> = emptyList(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 30.dp, horizontal = 15.dp)
//            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            CircularAvatar(
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(text = stringResource(R.string.image_storing_text))
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onChangeFolder()
                    }
                )
            }

            Row {
                Icon(
                    imageVector = Icons.Outlined.AddCircleOutline,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onChangeFolder()
                    }
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onChangeFolder()
                    }
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Outlined.GridView,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onChangeFolder()
                    }
                )

            }

        }

        Spacer(modifier = Modifier.width(20.dp))
        Column {
            IconButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(Icons.Default.History, null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = stringResource(R.string.captured_history_text))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            IconButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(Icons.Rounded.Wifi, null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = stringResource(R.string.offline_storing_text))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            IconButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(Icons.Default.Folder, null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = stringResource(R.string.created_folder_text))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        if(images.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.no_image_text),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(images) { image ->
                    CapturedImageItem(image) {
                        onImageClick(image)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ImageFolderScreenPreview() {
    SE121P11NewTheme {
        ImageFolderScreen(
            onChangeFolder = {},
            onImageClick = {},
//            images = images
        )
    }
}

val image = Image().apply {
    pictureUri = ""
    englishText = ""
    vietnameseText = ""
    imageName = "Image 1"
}

val images = listOf(image, image, image, image, image)