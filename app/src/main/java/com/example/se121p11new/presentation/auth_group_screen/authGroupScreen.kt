package com.example.se121p11new.presentation.auth_group_screen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.example.se121p11new.core.presentation.utils.AuthGroupScreenRoute
import com.example.se121p11new.core.presentation.utils.LoginScreenRoute

fun NavGraphBuilder.authGroupScreen(
    navController: NavHostController,

) {
    navigation<AuthGroupScreenRoute>(
        startDestination = LoginScreenRoute,
    ) {

//        composable<LoginScreenRoute> {
//            val context = LocalContext.cur
//            val authViewModel = hiltViewModel<AuthViewModel>()
//            val userState by authViewModel.userState.collectAsStateWithLifecycle()
//            val googleLauncher = getGoogleLauncher(
//                scope = lifecycleScope,
//                googleAuthClient = googleAuthClient,
//                onSignInResult = authViewModel::onSignInResult
//            )
//            val callbackManager = remember {
//                CallbackManager.Factory.create()
//            }
//            val facebookLauncher = getFacebookLauncher(
//                onSignInResult = authViewModel::onSignInResult,
//                scope = lifecycleScope,
//                callbackManager = callbackManager,
//                context = applicationContext,
//                facebookAuthClient = facebookAuthClient
//            )
//
//            LaunchedEffect(key1 = userState.isSignInSuccessful) {
//                if (userState.isSignInSuccessful) {
//                    Toast.makeText(
//                        applicationContext,
//                        "Sign in success",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    authViewModel.resetState()
//                }
//            }
//
//            LaunchedEffect(key1 = Unit) {
//                if (authClient.getSignInUser() != null) {
//                    navController.navigate("profile")
//                }
//            }
//
//            LoginScreen(
//                navigateToSignUp = {
//                    navController.navigate(SignUpScreenRoute)
//                },
//                onSignInWithGoogleClick = {
//                    this@MainActivity.providerType = googleAuthClient.providerType
//                    lifecycleScope.launch {
//                        val signInIntentSender =
//                            googleAuthClient.buildIntentSender()
//                        googleLauncher.launch(
//                            IntentSenderRequest.Builder(
//                                signInIntentSender ?: return@launch
//                            ).build()
//                        )
//                    }
//                },
//                onSignInWithFacebookClick = {
//                    this@MainActivity.providerType = facebookAuthClient.providerType
//                    facebookLauncher.launch(listOf("public_profile"))
//                },
//                onSignInWithTwitterClick = {
//                    this@MainActivity.providerType =
//                        twitterAuthUiClient.providerType
//                    lifecycleScope.launch {
//                        val signInResult =
//                            twitterAuthUiClient.signInWithPendingResult()
//                        authViewModel.onSignInResult(signInResult)
//                    }
//                },
//                onSignInAnonymouslyClick = {},
//                onSignInClick = {}
//            )
//        }
    }
}