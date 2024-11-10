package com.example.se121p11new.presentation.camera_screen

import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.se121p11new.presentation.camera_screen.components.CameraPreview

@Composable
fun CameraScreen(
    controller: LifecycleCameraController,
    takePhoto: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // camera preview

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9.0f / 16.0f)
                .clip(RoundedCornerShape(32.dp))
        ) {

            CameraPreview(
                controller = controller,
                modifier = Modifier.fillMaxSize()
            )
        }

        IconButton(
            onClick = {
                  controller.cameraSelector = if(controller.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
                      CameraSelector.DEFAULT_BACK_CAMERA
                  } else {
                      CameraSelector.DEFAULT_FRONT_CAMERA
                  }
            },
            modifier = Modifier.offset(y = 32.dp, x = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Cameraswitch,
                contentDescription = null
            )
        }

        Row(
            modifier = Modifier
                .offset(y = (-17).dp)
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomStart)
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = null
                )
            }

            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = null
                )
            }

            IconButton(
                onClick = {
                    takePhoto()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(red = 154, blue = 247, green = 0))
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    tint = Color.White,
                )
            }

            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = null
                )
            }

            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = null
                )
            }

        }
    }
}
