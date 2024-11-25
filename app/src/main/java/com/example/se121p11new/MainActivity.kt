package com.example.se121p11new

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.se121p11new.core.presentation.utils.CameraScreenRoute
import com.example.se121p11new.core.presentation.utils.CapturedImagePreviewScreenRoute
import com.example.se121p11new.core.presentation.utils.DashboardScreenRoute
import com.example.se121p11new.core.presentation.utils.ImageCaptioningScreenRoute
import com.example.se121p11new.core.presentation.utils.LoginScreenRoute
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.core.presentation.utils.SignUpScreenRoute
import com.example.se121p11new.core.presentation.utils.getBitmapFromUri
//import com.example.se121p11new.core.presentation.utils.takePhoto
import com.example.se121p11new.presentation.auth_group_screen.AuthViewModel
import com.example.se121p11new.presentation.auth_group_screen.UserData
import com.example.se121p11new.presentation.auth_group_screen.auth_client.AuthClient
import com.example.se121p11new.presentation.auth_group_screen.auth_client.FacebookAuthClient
import com.example.se121p11new.presentation.auth_group_screen.auth_client.GoogleAuthClient
import com.example.se121p11new.presentation.auth_group_screen.auth_client.TwitterAuthUiClient
import com.example.se121p11new.presentation.camera_screen.CameraScreen
import com.example.se121p11new.presentation.dashboard_screen.DashboardScreen
import com.example.se121p11new.presentation.captured_image_preview_screen.CapturedImagePreviewScreen
import com.example.se121p11new.presentation.image_captioning_screen.ImageCaptioningScreen
import com.example.se121p11new.presentation.image_captioning_screen.ImageCaptioningViewModel
import com.example.se121p11new.presentation.auth_group_screen.login_screen.LoginScreen
import com.example.se121p11new.presentation.auth_group_screen.sign_up_screen.SignUpScreen
import com.example.se121p11new.presentation.dashboard_screen.DashboardViewModel
import com.example.se121p11new.ui.theme.SE121P11NewTheme
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val googleAuthClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val facebookAuthClient by lazy {
        FacebookAuthClient()
    }

    private val twitterAuthUiClient by lazy {
        TwitterAuthUiClient(this)
    }

    private var authClient = AuthClient()

    private var providerType = ""
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

                NavHost(
                    navController = navController,
                    startDestination = CameraScreenRoute
                ) {
                    composable<LoginScreenRoute> {
                        val authViewModel = hiltViewModel<AuthViewModel>()
                        val userState by authViewModel.userState.collectAsStateWithLifecycle()
                        val googleLauncher = getGoogleLauncher(
                            scope = lifecycleScope,
                            googleAuthClient = googleAuthClient,
                            onSignInResult = authViewModel::onSignInResult
                        )
                        val callbackManager = remember {
                            CallbackManager.Factory.create()
                        }
                        val facebookLauncher = getFacebookLauncher(
                            onSignInResult = authViewModel::onSignInResult,
                            scope = lifecycleScope,
                            callbackManager = callbackManager,
                            context = applicationContext,
                            facebookAuthClient = facebookAuthClient
                        )

                        LaunchedEffect(key1 = userState.isSignInSuccessful) {
                            if(userState.isSignInSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    "Sign in success",
                                    Toast.LENGTH_LONG
                                ).show()
                                authViewModel.resetState()
                            }
                        }

                        LaunchedEffect(key1 = Unit) {
                            if(authClient.getSignInUser() != null) {
                                navController.navigate("profile")
                            }
                        }

                        LoginScreen(
                            navigateToSignUp = {
                                navController.navigate(SignUpScreenRoute)
                            },
                            onSignInWithGoogleClick = {
                                this@MainActivity.providerType = googleAuthClient.providerType
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthClient.buildIntentSender()
                                    googleLauncher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            },
                            onSignInWithFacebookClick = {
                                this@MainActivity.providerType = facebookAuthClient.providerType
                                facebookLauncher.launch(listOf("public_profile"))
                            },
                            onSignInWithTwitterClick = {
                                this@MainActivity.providerType = twitterAuthUiClient.providerType
                                lifecycleScope.launch {
                                    val signInResult = twitterAuthUiClient.signInWithPendingResult()
                                    authViewModel.onSignInResult(signInResult)
                                }
                            },
                            onSignInAnonymouslyClick = {},
                            onSignInClick = {}
                        )
                    }

                    composable<SignUpScreenRoute> {
                        SignUpScreen(
                            navigateToLogin = {
                                navController.navigate(LoginScreenRoute)
                            },
                            signUp = {}
                        )
                    }

                    composable<CameraScreenRoute> {
                        CameraScreen(
                            controller = controller,
                            takePhoto = {
                                takePhoto(
                                    controller,
                                    imageSavedHandler = { uri, imageName ->
                                        navController.navigate(CapturedImagePreviewScreenRoute(
                                            uriString = uri.toString(),
                                            imageName = imageName
                                        ))
                                    }
                                )
                            }
                        )
                    }

                    composable<CapturedImagePreviewScreenRoute> {
                        val args = it.toRoute<CapturedImagePreviewScreenRoute>()
                        CapturedImagePreviewScreen(
                            uriString = args.uriString,
                            onSubmit = {
                                navController.navigate(ImageCaptioningScreenRoute(
                                    uriString = args.uriString,
                                    imageName = args.imageName,
                                ))
                            }
                        )
                    }

                    composable<ImageCaptioningScreenRoute> {
                        val imageCaptioningViewModel = hiltViewModel<ImageCaptioningViewModel>()
                        imageCaptioningViewModel.apiTurnOn = false
                        val args = it.toRoute<ImageCaptioningScreenRoute>()
                        LaunchedEffect(key1 = Unit) {
                            val bitmap = getBitmapFromUri(
                                uri = Uri.parse(args.uriString),
                                applicationContext = applicationContext
                            )
                            imageCaptioningViewModel.imageName = args.imageName
                            imageCaptioningViewModel.imageUri = args.uriString
                            imageCaptioningViewModel.generateText(bitmap)
                        }

                        val englishText by imageCaptioningViewModel.generatedEnglishText.collectAsStateWithLifecycle()
                        val vietnameseText by imageCaptioningViewModel.generatedVietnameseText.collectAsStateWithLifecycle()
                        ImageCaptioningScreen(
                            uri = args.uriString,
                            englishText = englishText,
                            vietnameseText = vietnameseText,
                            onBack = {
                                navController.navigate(CameraScreenRoute) {
                                    popUpTo(CameraScreenRoute)
                                }
                            }
                        )
                    }

                    composable<DashboardScreenRoute> {
                        val dashboardViewModel = hiltViewModel<DashboardViewModel>()
                        val images by dashboardViewModel.images.collectAsStateWithLifecycle()
                        LaunchedEffect(key1 = Unit) {
                            println("image $images")
                        }
                        DashboardScreen(
                            images = images,
                            onClick = { image ->
                                navController.navigate(
                                    ImageCaptioningScreenRoute(
                                        uriString = image.pictureUri,
                                        englishText = image.englishText,
                                        vietnameseText = image.vietnameseText,
                                        imageName = image.imageName
                                    )
                                )
                            }
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
        controller: LifecycleCameraController,
        imageSavedHandler: (Uri, String) -> Unit
    ) {
        if (!hasRequiredPermissions()) {
            return
        }

        val metadata = ImageCapture.Metadata().apply {
            isReversedHorizontal =
                (controller.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA)
        }

        val imageName = "${System.currentTimeMillis()}"
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
                    imageSavedHandler(outputFileResults.savedUri!!, imageName)
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

@Composable
fun getGoogleLauncher(
    scope: CoroutineScope,
    googleAuthClient: GoogleAuthClient,
    onSignInResult: (Resource<UserData>) -> Unit
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartIntentSenderForResult()
) { result ->
    if (result.resultCode == RESULT_OK) {
        scope.launch {
            val signInResult = googleAuthClient.getSignInResultFromIntent(
                intent = result.data ?: return@launch
            )
            onSignInResult(signInResult)
        }
    }
}

@Composable
fun getFacebookLauncher(
    scope: CoroutineScope,
    facebookAuthClient: FacebookAuthClient,
    onSignInResult: (Resource<UserData>) -> Unit,
    callbackManager: CallbackManager,
    context: Context
) = rememberLauncherForActivityResult(
    LoginManager.getInstance()
        .createLogInActivityResultContract(callbackManager)
) { result ->
    LoginManager.getInstance().onActivityResult(
        result.resultCode,
        result.data,
        object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                scope.launch {
                    val signInResult =
                        facebookAuthClient.handleFacebookAccessToken(
                            result.accessToken
                        )
                    onSignInResult(signInResult)
                }
            }

            override fun onCancel() {
                Toast.makeText(
                    context,
                    "Login cancelled",
                    Toast.LENGTH_LONG
                ).show()

            }

            override fun onError(error: FacebookException) {
                Toast.makeText(
                    context,
                    "Facebook login error: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    )
}
