package com.example.se121p11new.presentation.dashboard_screen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.components.CircularAvatar
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.core.presentation.components.CapturedImageItem
import com.example.se121p11new.core.presentation.utils.SelectItem
import com.example.se121p11new.presentation.dashboard_screen.components.SavedVocabularyItem
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun DashboardScreen(
    images: List<Image>,
    modifier: Modifier = Modifier,
    onClick: (Image) -> Unit,
    onDeleteImage: (Image) -> Unit,
    onGoToImageFolder: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 15.dp)
            .verticalScroll(rememberScrollState())
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.captured_image_text),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .clickable {
                            onGoToImageFolder()
                        }
                ) {
                    Text(
                        text = stringResource(R.string.more_text)
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                }

            }

            Spacer(modifier = Modifier.height(15.dp))

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
                        CapturedImageItem(
                            image = image,
                            onClick = onClick,
                            onDeleteImage = onDeleteImage
                        )
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(25.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.saved_vocabulary_text),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .clickable {

                        }
                ) {
                    Text(
                        text = stringResource(R.string.more_text)
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                }

            }

            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(4) { item ->
                    SavedVocabularyItem()
                }

            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    SE121P11NewTheme {
        DashboardScreen(
            images = listOf(image, image, image),
            onGoToImageFolder = {},
            onClick = {},
            onDeleteImage = {}
        )
    }
}

val image = Image().apply {
    pictureUri = ""
    englishText = ""
    vietnameseText = ""
    imageName = "Image 1"
}