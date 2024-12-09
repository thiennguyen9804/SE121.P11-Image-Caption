package com.example.se121p11new.presentation.camera_group_screen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.se121p11new.core.presentation.utils.CameraGroupScreenRoute
import com.example.se121p11new.core.presentation.utils.CameraScreenRoute
import com.example.se121p11new.core.presentation.utils.CapturedImagePreviewScreenRoute
import com.example.se121p11new.core.presentation.utils.ImageCaptioningScreenRoute
import com.example.se121p11new.core.presentation.utils.sharedViewModel
import com.example.se121p11new.presentation.camera_group_screen.camera_screen.CameraScreen
import com.example.se121p11new.presentation.camera_group_screen.camera_screen.takePhoto
import com.example.se121p11new.presentation.camera_group_screen.captured_image_preview_screen.CapturedImagePreviewScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.cameraGroupScreen(
    applicationContext: Context,
    navController: NavHostController,
    controller: LifecycleCameraController,
) {
    navigation<CameraGroupScreenRoute>(
        startDestination = CameraScreenRoute
    ) {
        composable<CameraScreenRoute> {
            val sharedViewModel = it.sharedViewModel<CameraGroupViewModel>(navController)
            CameraScreen(
                controller = controller,
                takePhoto = {
                    takePhoto(
                        controller = controller,
                        applicationContext = applicationContext,
                        onPhotoTaken = { bitmap ->
                            navController.navigate(CapturedImagePreviewScreenRoute)
                            sharedViewModel.setCapturedBitmap(bitmap)
                        }
                    )
                }
            )
        }

        composable<CapturedImagePreviewScreenRoute> {
            val sharedViewModel = it.sharedViewModel<CameraGroupViewModel>(navController)
            CapturedImagePreviewScreen(
                bitmap = sharedViewModel.bitmap,
                onSubmit = {
                    val imageName = "${System.currentTimeMillis()}.jpg"
                    val file = File(applicationContext.filesDir, imageName)
                    navController.navigate(
                        ImageCaptioningScreenRoute(
                            uriString = file.toUri().toString()
                        )
                    ) {
                        popUpTo<CapturedImagePreviewScreenRoute> {
                            inclusive = true
                        }
                    }
                    sharedViewModel.writeBitmapToInternalStorage(file)


//                    val string = file.toUri().toString()
//                    println(string)
                }
            )
        }
    }
}