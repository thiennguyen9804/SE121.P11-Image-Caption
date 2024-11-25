package com.example.se121p11new.core.presentation.utils

import android.app.Activity
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.se121p11new.ImageAIApplication
import io.realm.kotlin.internal.RealmInitializer.Companion.filesDir
import java.io.File

private fun takePhoto(
    controller: LifecycleCameraController,
    hasRequiredPermissions: Boolean,
//    applicationContext: Context,
    imageSavedHandler: (Uri) -> Unit
) {
    if (hasRequiredPermissions) {
        return
    }

    val metadata = ImageCapture.Metadata().apply {
        isReversedHorizontal =
            (controller.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA)
    }
    val applicationContext = ImageAIApplication().applicationContext

    val imageName = "${System.currentTimeMillis()}"
//    sharedViewModel.setImageName("$imageName.jpg")
//    imageCaptioningViewModel.imageName = imageName
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            File(filesDir, imageName)
        )
        .setMetadata(metadata)
        .build()

    controller.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(applicationContext),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                outputFileResults.savedUri?.let { imageSavedHandler(it) }
//                sharedViewModel.updateBitmap(bitmap)
//                navController.navigate(CapturedImagePreviewScreen)
//                println("image uri ${outputFileResults.savedUri.toString()}")
//                sharedViewModel.imageUri = outputFileResults.savedUri.toString()


                Toast.makeText(
                    applicationContext,
                    "Succeed!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(exception: ImageCaptureException) {
                Toast.makeText(
                    applicationContext,
                    "Something went wrong!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )
}