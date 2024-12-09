package com.example.se121p11new.presentation.camera_group_screen.captured_image_preview_screen

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.se121p11new.presentation.camera_group_screen.captured_image_preview_screen.components.SubmitButton
import com.example.se121p11new.ui.theme.SE121P11NewTheme
import java.io.File


@Composable
fun CapturedImagePreviewScreen(
    bitmap: Bitmap,
//    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    // TODO chuyển launch effect ra ngoài sub graph
    val context = LocalContext.current as ComponentActivity
    var dontDeleteFlag = false
    LaunchedEffect(key1 = true) {

        context.enableEdgeToEdge()
    }

//    LaunchedEffect(key1 = dontDeleteFlag) {
//        println("don't delete flag $dontDeleteFlag")
//    }

//    val lifecycleOwner = LocalLifecycleOwner.current
//    DisposableEffect(lifecycleOwner) {
//
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_STOP && !dontDeleteFlag) {
//                val file = File(uriString)
//                val deleted = file.delete()
//                if(!deleted) {
//                    file.delete()
//                }
//            }
//        }
//
//        lifecycleOwner.lifecycle.addObserver(observer)
//
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }

//    BackHandler(!dontDeleteFlag) {
//        val file = File(uriString)
//        val deleted = file.delete()
//        println("file deleted successful $deleted")
//        onBack()
//    }

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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(bitmap)
                    .build(),
                contentDescription = "Captured Image",
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .offset(y = (-60).dp),
            horizontalArrangement = Arrangement.End
        ) {
            SubmitButton(
                modifier = Modifier
                    .width(90.dp)
                    .height(35.dp)
                    .offset(x = (-20).dp)
            ) {
                dontDeleteFlag = true
                onSubmit()
            }
        }


    }
}

@Preview
@Composable
private fun CapturedImagePreviewScreenPreview() {
    SE121P11NewTheme {
        CapturedImagePreviewScreen(
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        ) {

        }
    }
}