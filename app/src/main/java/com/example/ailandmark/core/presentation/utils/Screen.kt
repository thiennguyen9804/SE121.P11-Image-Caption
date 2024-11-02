package com.example.ailandmark.core.presentation.utils

sealed class Screen(val route: String) {
    data object LoginScreen : Screen("login_screen")
    data object CameraScreen : Screen("camera_screen")
    data object DescriptionScreen : Screen("description_screen")
}