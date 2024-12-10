package com.example.se121p11new.core.presentation.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

//
@Stable
class AppState(
    private val navController: NavHostController
) {

    fun navigateToRoute(route: Any) {
        navController.navigate(route) {
            launchSingleTop
        }


    }

    val shouldShowBottomBar: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route != CameraScreenRoute::class.toString().substringAfter("class ")
}

@SuppressLint("ComposableNaming")
@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) : AppState {
    return remember(navController) {
        AppState(navController)
    }
}