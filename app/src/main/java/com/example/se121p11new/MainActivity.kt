package com.example.se121p11new

import android.app.Activity.RESULT_OK
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.se121p11new.core.presentation.utils.CameraGroupScreenRoute
import com.example.se121p11new.core.presentation.utils.DashboardScreenRoute
import com.example.se121p11new.core.presentation.utils.ImageCaptioningScreenRoute
import com.example.se121p11new.core.presentation.utils.ImageFolderGroupScreenRoute
import com.example.se121p11new.core.presentation.utils.LoginScreenRoute
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.core.presentation.utils.SignUpScreenRoute
import com.example.se121p11new.core.presentation.utils.VocabularyDetailScreenRoute
import com.example.se121p11new.core.presentation.utils.VocabularyFolderGroupScreenRoute
import com.example.se121p11new.core.presentation.utils.bottomNavItems
import com.example.se121p11new.core.presentation.utils.getBitmapFromUri
import com.example.se121p11new.core.presentation.utils.rememberAppState
import com.example.se121p11new.core.presentation.utils.routeToIndexes
import com.example.se121p11new.core.presentation.utils.setStatusBarColor
import com.example.se121p11new.presentation.auth_group_screen.AuthViewModel
import com.example.se121p11new.presentation.auth_group_screen.UserData
import com.example.se121p11new.presentation.auth_group_screen.auth_client.AuthClient
import com.example.se121p11new.presentation.auth_group_screen.auth_client.FacebookAuthClient
import com.example.se121p11new.presentation.auth_group_screen.auth_client.GoogleAuthClient
import com.example.se121p11new.presentation.auth_group_screen.auth_client.TwitterAuthUiClient
import com.example.se121p11new.presentation.auth_group_screen.login_screen.LoginScreen
import com.example.se121p11new.presentation.auth_group_screen.sign_up_screen.SignUpScreen
import com.example.se121p11new.presentation.camera_group_screen.cameraGroupScreen
import com.example.se121p11new.presentation.camera_group_screen.camera_screen.CAMERAX_PERMISSIONS
import com.example.se121p11new.presentation.camera_group_screen.camera_screen.hasRequiredPermissions
import com.example.se121p11new.presentation.dashboard_screen.DashboardScreen
import com.example.se121p11new.presentation.dashboard_screen.DashboardViewModel
import com.example.se121p11new.presentation.image_captioning_screen.ImageCaptioningScreen
import com.example.se121p11new.presentation.image_captioning_screen.ImageCaptioningViewModel
import com.example.se121p11new.presentation.image_folder_group_screen.imageFolderGroupScreen
import com.example.se121p11new.presentation.vocabulary_detail_screen.VocabularyDetailScreen
import com.example.se121p11new.presentation.vocabulary_detail_screen.VocabularyDetailViewModel
import com.example.se121p11new.presentation.vocabulary_folder_group_screen.vocabularyFolderGroupScreen
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
import java.util.Locale

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

    private val TAG = "NavigationBar"
//    private lateinit var imageCaptioningViewModel: ImageCaptioningViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions(applicationContext)) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }
        Log.d(TAG, routeToIndexes.toString())
        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    scrim = android.graphics.Color.WHITE,
                    darkScrim = android.graphics.Color.BLACK
                )
            )

            SE121P11NewTheme {
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE
                        )
                    }
                }


                navController = rememberNavController()
                navController.addOnDestinationChangedListener { controller, destination, arguments ->
                    selectedItemIndex = routeToIndexes[destination.route] ?: selectedItemIndex
                }
                val appState = rememberAppState(navController)

//                Log.d(TAG, appState.shouldShowBottomBar.toString())
                Scaffold(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    bottomBar = {
                        if(appState.shouldShowBottomBar) {
                            NavigationBar(
                                modifier = Modifier.height(50.dp),
                                containerColor = Color.White,
                                contentColor = Color.Black,
                            ) {
                                bottomNavItems.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        colors = NavigationBarItemDefaults.colors().copy(
                                            selectedIndicatorColor = Color.Transparent
                                        ),
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            if(selectedItemIndex == index) {
                                                return@NavigationBarItem
                                            }

                                            navController.popBackStack(item.route, inclusive = true)
                                            navController.navigate(item.route) {
                                                launchSingleTop
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (selectedItemIndex == index)
                                                    item.selectedIcon else item.unselectedIcon,
                                                contentDescription = null,
                                                tint = if (selectedItemIndex != index)
                                                    Color.Black else Color(0xff9A00F7)
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { contentPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = DashboardScreenRoute,
                        modifier = if(appState.shouldShowTopBar) Modifier.padding(contentPadding) else Modifier.padding(top = 0.dp)

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
                                if (userState.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in success",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    authViewModel.resetState()
                                }
                            }

                            LaunchedEffect(key1 = Unit) {
                                if (authClient.getSignInUser() != null) {
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
                                        val signInIntentSender =
                                            googleAuthClient.buildIntentSender()
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
                                    this@MainActivity.providerType =
                                        twitterAuthUiClient.providerType
                                    lifecycleScope.launch {
                                        val signInResult =
                                            twitterAuthUiClient.signInWithPendingResult()
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

                        cameraGroupScreen(
                            applicationContext = applicationContext,
                            controller = controller,
                            navController = navController
                        )

                        imageFolderGroupScreen(
                            navController = navController,
                            onBack = {}
                        )

                        composable<ImageCaptioningScreenRoute> {
                            val imageCaptioningViewModel = hiltViewModel<ImageCaptioningViewModel>()
                            val vocabularyDetailViewModel =
                                hiltViewModel<VocabularyDetailViewModel>()
                            val args = it.toRoute<ImageCaptioningScreenRoute>()
                            val imageUri = Uri.parse(args.uriString)
                            imageCaptioningViewModel.setMyImageUri(imageUri)
                            LaunchedEffect(key1 = Unit) {
                                imageCaptioningViewModel.getRealmImage()
                                val bitmap = getBitmapFromUri(
                                    uri = Uri.parse(args.uriString),
                                    applicationContext = applicationContext
                                )
                                imageCaptioningViewModel.setMyBitmap(bitmap)
                                imageCaptioningViewModel.getImageByUri()
                            }
                            val englishText by
                                imageCaptioningViewModel.generatedEnglishText.collectAsStateWithLifecycle()
                            val vietnameseText by
                                imageCaptioningViewModel.generatedVietnameseText.collectAsStateWithLifecycle()
                            val imageName by
                                imageCaptioningViewModel.imageName.collectAsStateWithLifecycle()
                            val captureTime by
                                imageCaptioningViewModel.captureTime.collectAsStateWithLifecycle()

                            ImageCaptioningScreen(
                                uri = args.uriString,
                                englishText = englishText,
                                vietnameseText = vietnameseText,
                                imageName = imageName,
                                capturedTime = captureTime,
                                onGoToVocabularyDetail = { engVocab ->
                                    navController.navigate(
                                        VocabularyDetailScreenRoute(
                                            engVocab = engVocab
                                        )
                                    )
                                },
                                onBack = {
                                    vocabularyDetailViewModel.clearCache()
                                    navController.popBackStack()
                                },
                                onSaveVocabulary = { engVocab ->
                                    imageCaptioningViewModel.saveVocabularyLocally(engVocab)
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Đã lưu từ vựng!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onDeleteVocabulary = { engVocab ->
                                    imageCaptioningViewModel.deleteVocabularyLocally(engVocab)
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Đã xóa từ vựng!",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                },
                            )
                        }

                        vocabularyFolderGroupScreen(navController = navController)

                        composable<DashboardScreenRoute>() {
                            val dashboardViewModel = hiltViewModel<DashboardViewModel>()
                            val images by dashboardViewModel.images.collectAsStateWithLifecycle()
                            val imageFolderList by dashboardViewModel.imageFolderList.collectAsStateWithLifecycle()
                            DashboardScreen(
                                images = images,
                                onClick = { image ->
                                    navController.navigate(
                                        ImageCaptioningScreenRoute(
                                            uriString = image.pictureUri,
                                        )
                                    )
                                },
                                allImageFolder = imageFolderList,
                                onDeleteImage = dashboardViewModel::deleteImage,
                                onGoToImageFolder = {
                                    navController.navigate(ImageFolderGroupScreenRoute)
                                },
                                onGotoVocabularyFolder = {
                                    navController.navigate(VocabularyFolderGroupScreenRoute)
                                },

                                onAddImageToFolder = dashboardViewModel::addImageToFolder,
                                onRemoveImageOutOfFolder = dashboardViewModel::removeImageOutOfFolder,
                            )
                        }
                        //
                        composable<VocabularyDetailScreenRoute> {
                            val args = it.toRoute<VocabularyDetailScreenRoute>()
                            val vocabularyDetailViewModel =
                                hiltViewModel<VocabularyDetailViewModel>()
                            val engVocab = args.engVocab.lowercase(Locale.ENGLISH)
                                .replace(Regex("\\p{Punct}"), "").lowercase()
                            LaunchedEffect(key1 = Unit) {
                                vocabularyDetailViewModel.getVocabulary(engVocab)
                            }
                            val vocabulary by vocabularyDetailViewModel.vocabulary.collectAsStateWithLifecycle()
                            VocabularyDetailScreen(
                                engWord = engVocab,
                                vocabulary = vocabulary
                            )
                        }
                    }
                }

            }
        }
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