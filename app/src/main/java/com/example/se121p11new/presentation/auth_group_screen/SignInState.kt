package com.example.se121p11new.presentation.auth_group_screen

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
)