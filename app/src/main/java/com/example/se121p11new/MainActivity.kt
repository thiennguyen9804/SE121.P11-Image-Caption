package com.example.se121p11new

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.se121p11new.core.presentation.utils.CameraScreen
import com.example.se121p11new.core.presentation.utils.CapturedImagePreviewScreen
import com.example.se121p11new.core.presentation.utils.DashboardScreen
import com.example.se121p11new.core.presentation.utils.ImageCaptioningScreen
import com.example.se121p11new.core.presentation.utils.LoginScreen
import com.example.se121p11new.core.presentation.utils.SignUpScreen
import com.example.se121p11new.presentation.camera_screen.CameraScreen
import com.example.se121p11new.presentation.dashboard_screen.DashboardScreen
import com.example.se121p11new.presentation.captured_image_preview_screen.CapturedImagePreviewScreen
import com.example.se121p11new.presentation.login_screen.LoginScreen
import com.example.se121p11new.presentation.shared_view_model.SharedViewModel
import com.example.se121p11new.presentation.sign_up_screen.SignUpScreen
import com.example.se121p11new.ui.theme.SE121P11NewTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var viewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }
        setContent {
            this.enableEdgeToEdge()
            SE121P11NewTheme {
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
                    startDestination = CameraScreen
                ) {

                    composable<LoginScreen> {
                        LoginScreen(
                            navigateToSignUp = {
                                navController.navigate(SignUpScreen)
                            },
                            signIn = {}
                        )
                    }

                    composable<SignUpScreen> {
                        SignUpScreen(
                            navigateToLogin = {
                                navController.navigate(LoginScreen)
                            },
                            signUp = {}
                        )
                    }

                    composable<CameraScreen> {
                        CameraScreen(
                            controller = controller,
                        ) {
                            takePhoto(controller)
                        }
                    }

                    composable<CapturedImagePreviewScreen> {
                        val bitmap by viewModel.bitmap.collectAsStateWithLifecycle()
                        val imageName by viewModel.imageName.collectAsStateWithLifecycle()
                        CapturedImagePreviewScreen(bitmap, imageName) {
//                            viewModel.
                        }
                    }

                    composable<ImageCaptioningScreen> {

                    }

                    composable<DashboardScreen> {
                        DashboardScreen()
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
        if (!hasRequiredPermissions()) {
            return
        }

        val metadata = ImageCapture.Metadata().apply {
            isReversedHorizontal =
                (controller.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA)
        }

        viewModel.setImageName("${System.currentTimeMillis()}.jpg")
//        viewModel.normalImageName = "${System.currentTimeMillis()}.jpg"
        println("image name ${viewModel.normalImageName}")

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                File(filesDir, viewModel.imageName.value)
            )
            .setMetadata(metadata)
            .build()

        controller.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(applicationContext),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        outputFileResults.savedUri?.let {
                            ImageDecoder.createSource(
                                applicationContext.contentResolver,
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
                    navController.navigate(CapturedImagePreviewScreen)
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
