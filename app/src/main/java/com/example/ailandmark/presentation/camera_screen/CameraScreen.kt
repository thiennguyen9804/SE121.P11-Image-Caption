package com.example.ailandmark.presentation.camera_screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.util.Rational
import android.view.OrientationEventListener
import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ailandmark.SharedViewModel
import com.example.ailandmark.presentation.camera_screen.components.CameraPreview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

@Composable
fun CameraScreen(
    navController: NavController,
    controller: LifecycleCameraController,
    viewModel: SharedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val resolutionSelector: ResolutionSelector = ResolutionSelector.Builder()
        .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
        .build()
    val imageCapture = remember {
        ImageCapture.Builder()
            .setResolutionSelector(resolutionSelector)
//            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build().apply {
                setCropAspectRatio(Rational(9, 16))
            }
    }

    val orientationEventListener = object : OrientationEventListener(context) {
        override fun onOrientationChanged(orientation : Int) {
            // Monitors orientation values to determine the target rotation value
            val rotation : Int = when (orientation) {
                in 45..134 -> Surface.ROTATION_270
                in 135..224 -> Surface.ROTATION_180
                in 225..314 -> Surface.ROTATION_90
                else -> Surface.ROTATION_0
            }

            imageCapture.targetRotation = rotation
        }
    }
    orientationEventListener.enable()
    val lensFacing = remember {
        mutableIntStateOf(CameraSelector.LENS_FACING_BACK)
    }

//    val startColor = Color.Red.copy(alpha = .05f)
//    val endColor = Color.Red.copy(alpha = .8f)
//    val sizeAnimationDuration = 200
//    val colorAnimationDuration = 200
//    val boxSize = 100.dp
//    var touchedPoint by remember { mutableStateOf(Offset.Zero) }
//    var visible by remember { mutableStateOf(false) }
//    val sizeAnimation by animateDpAsState(
//        if (visible) boxSize else 0.dp,
//        tween(
//            durationMillis = sizeAnimationDuration,
//            easing = LinearEasing
//        )
//    )
//    val colorAnimation by animateColorAsState(
//        if (visible) startColor else endColor,
//        animationSpec = tween(
//            durationMillis = colorAnimationDuration,
//            easing = LinearEasing
//        ),
//        finishedListener = {
//            visible = false
//        }
//    )

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectTapGestures {
//                    touchedPoint = it
//                    visible = true
//                }
//            }
    ) {
//        val density = LocalDensity.current
//        val (xDp, yDp) = with(density) {
//            (touchedPoint.x.toDp() - boxSize / 2) to (touchedPoint.y.toDp() - boxSize / 2)
//        }
//
//        Box(
//            modifier = Modifier
//                .offset(xDp, yDp)
//                .size(boxSize)
//                .background(Color.Red)
//        ) {
//            Box(
//                Modifier
//                    .align(Alignment.Center)
//                    .background(colorAnimation, CircleShape)
//                    .height(if (visible) sizeAnimation else 0.dp)
//                    .width(if (visible) sizeAnimation else 0.dp),
//            )
//        }

        CameraPreview(
            resolutionSelector = resolutionSelector,
            imageCapture = imageCapture,
            lensFacing = lensFacing.intValue
        )

        IconButton(
            onClick = {
                lensFacing.intValue = if(lensFacing.intValue == CameraSelector.LENS_FACING_BACK) {
                    CameraSelector.LENS_FACING_FRONT
                } else CameraSelector.LENS_FACING_BACK
            },
            modifier = Modifier.offset(16.dp)
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
                    takePhoto(
                        imageCapture = imageCapture,
                        context = context,
                        onPictureSaved = viewModel::onPictureSaved,
                        lensFacing = lensFacing.intValue
                    )
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

private fun takePhoto(
    imageCapture: ImageCapture,
    context: Context,
    onPictureSaved: (Uri) -> Unit,
//    controller: LifecycleCameraController
//    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    lensFacing: Int = 0
) {
//    val name = "CameraxImage.jpeg"
//    val contentValues = ContentValues().apply {
//        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
//        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
//            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
//        }
//    }
//    val outputOptions = ImageCapture.OutputFileOptions
//        .Builder(
//            context.contentResolver,
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            contentValues
//        )
//        .build()
    imageCapture.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(imageProxy: ImageProxy) {
                super.onCaptureSuccess(imageProxy)
                CoroutineScope(Dispatchers.IO).launch {
                    var bitmapImage = imageProxy.toBitmap()
                    if(lensFacing == CameraSelector.LENS_FACING_FRONT) {
                        val matrix = Matrix().apply {
                            postScale(-1.0f, 1.0f)
                        }

                        val transformedBitmap = Bitmap.createBitmap(
                            imageProxy.toBitmap(),
                            0,
                            0,
                            imageProxy.width,
                            imageProxy.height,
                            matrix,
                            true
                        )
                        bitmapImage = transformedBitmap
                    }
                    val stream = ByteArrayOutputStream()
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    val imageBytes = stream.toByteArray()
                    val file = File(context.filesDir, "${System.currentTimeMillis()}.jpeg")
                    FileOutputStream(file).use {
                        it.write(imageBytes);
                    }
                    onPictureSaved(file.toUri())
                }
            }


        })
}
