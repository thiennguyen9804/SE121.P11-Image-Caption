package com.example.ailandmark

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ailandmark.core.presentation.utils.Screen
import com.example.ailandmark.presentation.camera_screen.CameraScreen
import com.example.ailandmark.presentation.description_screen.DescriptionScreen
import com.example.ailandmark.presentation.login_screen.LoginScreen
import com.example.ailandmark.ui.theme.AILandmarkTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var viewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }
        setContent {
            AILandmarkTheme {
                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE
                        )
                    }
                }

                navController = rememberNavController()
                viewModel = hiltViewModel<SharedViewModel>()

                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(Screen.CameraScreen.route) {
                        CameraScreen(
                            controller = controller,
                        ) {
                            takePhoto(controller)
                        }
                    }

                    composable(Screen.DescriptionScreen.route) {
                        val bitmap by viewModel.bitmap.collectAsState()
                        DescriptionScreen(bitmap)
                    }

                    composable(Screen.LoginScreen.route) {
                        LoginScreen(
                            navigateToSignUp = {},
                            signIn = {}
                        )
                    }
                }
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun takePhoto(
        controller: LifecycleCameraController
    ) {
        if(!hasRequiredPermissions()) {
            return
        }

        val metadata = ImageCapture.Metadata().apply {
            isReversedHorizontal = (controller.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA)
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                File(filesDir, "${System.currentTimeMillis()}.jpg")
            )
            .setMetadata(metadata)
            .build()

        controller.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(applicationContext),
            object: ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        outputFileResults.savedUri?.let {
                            ImageDecoder.createSource(applicationContext.contentResolver,
                                it
                            )
                        }?.let { ImageDecoder.decodeBitmap(it) }
                    } else {
                        MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver,
                            outputFileResults.savedUri
                        )
                    }

                    viewModel.updateBitmap(bitmap)
                    navController.navigate("description_screen")
                    println("image uri ${outputFileResults.savedUri.toString()}")

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

                    Timber.tag("Capture Error").e(exception)
                }

            }
        )
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    }
}
