package com.example.se121p11new.core.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    BottomNavItem(
        selectedIcon = Icons.Filled.Image,
        unselectedIcon = Icons.Outlined.Image
    ),
    BottomNavItem(
        selectedIcon = Icons.Filled.Camera,
        unselectedIcon = Icons.Outlined.Camera
    ),
    BottomNavItem(
        selectedIcon = Icons.AutoMirrored.Filled.LibraryBooks,
        unselectedIcon = Icons.AutoMirrored.Outlined.LibraryBooks
    ),
    BottomNavItem(
        selectedIcon = Icons.Filled.Image,
        unselectedIcon = Icons.Outlined.Image
    ),

)