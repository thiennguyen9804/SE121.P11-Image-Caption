package com.example.ailandmark.presentation.camera_screen.components

import android.content.Context
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.camera.view.PreviewView.ScaleType
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraPreview(
    imageCapture: ImageCapture,
    resolutionSelector: ResolutionSelector,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK
) {

    val curScaleType = ScaleType.FILL_CENTER
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val preview = Preview.Builder()
        .setResolutionSelector(resolutionSelector)
        .build()
    val previewView = remember {
        PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            scaleType = curScaleType
        }
    }
    var cameraxSelector: CameraSelector

    val useCaseGroup = UseCaseGroup.Builder()
        .addUseCase(preview)
        .addUseCase(imageCapture)
        .build()
        ImageCapture.Builder().build()

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraxSelector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()
        val camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, useCaseGroup)
        preview.surfaceProvider = previewView.surfaceProvider
        setUpZoomTapToFocus(
            camera = camera,
            previewView = previewView,
            context = context
        )
    }

//    val sizeAnimationDuration = 200
//    val colorAnimationDuration = 200
//    val boxSize = 100.dp
//    var touchedPoint by remember { mutableStateOf(Offset.Zero) }
//    var visible by remember { mutableStateOf(false) }

    AndroidView(
        factory = { previewView },
        modifier = Modifier
            .aspectRatio((9f/16f), matchHeightConstraintsFirst = true)
    ) {

    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

private fun setUpZoomTapToFocus(
    previewView: PreviewView,
    camera: Camera,
    context: Context
) {
    val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener(){
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val currentZoomRatio = camera.cameraInfo.zoomState.value?.zoomRatio ?: 1f
            val delta = detector.scaleFactor
            camera.cameraControl.setZoomRatio(currentZoomRatio * delta)
            return true
        }
    }

    val scaleGestureDetector = ScaleGestureDetector(context, listener)
    previewView.setOnTouchListener { view, event ->
        scaleGestureDetector.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_DOWN){
            val factory = previewView.meteringPointFactory
            val point = factory.createPoint(event.x,event.y)
            val action = FocusMeteringAction.Builder(point,FocusMeteringAction.FLAG_AF)
                .setAutoCancelDuration(2,TimeUnit.SECONDS)
                .build()

            val x = event.x
            val y = event.y

//            val focusCircle = RectF(x-50,y-50, x+50,y+50)

//            mainBinding.focusCircleView.focusCircle = focusCircle
//            mainBinding.focusCircleView.invalidate()

            camera.cameraControl.startFocusAndMetering(action)

            view.performClick()
        }
        true
    }
}
