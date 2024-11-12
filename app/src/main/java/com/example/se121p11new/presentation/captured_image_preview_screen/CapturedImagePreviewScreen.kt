package com.example.se121p11new.presentation.captured_image_preview_screen

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.se121p11new.presentation.camera_screen.components.CameraPreview
import com.example.se121p11new.presentation.captured_image_preview_screen.components.SubmitButton
import java.io.File


@Composable
fun CapturedImagePreviewScreen(
    bitmap: Bitmap?,
    imageName: String = "",
    onSubmit: () -> Unit
) {
    // TODO chuyển launch effect ra ngoài sub graph
    val context = LocalContext.current as ComponentActivity
    LaunchedEffect(key1 = true) {
        context.enableEdgeToEdge()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                val dir: File = context.filesDir
                val file = File(dir, imageName)
                val deleted = file.delete()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .aspectRatio(9.0f / 16.0f)

        ) {
            bitmap?.asImageBitmap()?.let {
                Image(
                    bitmap = it,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-32).dp)
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            SubmitButton(
                modifier = Modifier
                    .size(90.dp)
                    .height(35.dp)
            ) {

            }
        }

    }
}