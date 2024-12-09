package com.example.se121p11new.presentation.camera_group_screen.camera_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.example.se121p11new.MainActivity
import com.example.se121p11new.core.presentation.utils.StringFromTime
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
fun takePhoto(
    applicationContext: Context,
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit
) {
    if (!hasRequiredPermissions(applicationContext)) {
        return
    }

    val metadata = ImageCapture.Metadata().apply {
        isReversedHorizontal =
            (controller.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA)

    }

//    val imageName = StringFromTime.buildPictureName()
//    val capturedTime = StringFromTime.buildDateTimeString()
//    val outputOptions = ImageCapture.OutputFileOptions
//        .Builder(
//            File(applicationContext.cacheDir, "${System.currentTimeMillis()}.jpg")
//
//        )
//        .setMetadata(metadata)
//        .build()

    controller.takePicture(
        ContextCompat.getMainExecutor(applicationContext),
        object: ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                if(controller.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
                    val matrix = Matrix().apply {
                        postScale(-1.0f, 1.0f)
                    }

                    val transformedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )
                    onPhotoTaken(transformedBitmap)
                } else {
                    onPhotoTaken(image.toBitmap())
                }
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception)
            }

        }
    )
}

fun hasRequiredPermissions(
    applicationContext: Context
): Boolean {
    return CAMERAX_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            applicationContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}

val CAMERAX_PERMISSIONS = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE,
)