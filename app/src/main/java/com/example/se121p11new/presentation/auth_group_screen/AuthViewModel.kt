package com.example.se121p11new.presentation.auth_group_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.se121p11new.core.presentation.utils.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/*
TODO auth viewmodel sẽ trở thành shared viewmodel cho auth group trong tương lai
*/
@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val TAG = "AuthViewModel"
    private val _userState = MutableStateFlow(SignInState())
    val userState = _userState.asStateFlow()

    fun onSignInResult(result: Resource<UserData>) {
        Log.d(TAG, result.data?.username.toString())
        _userState.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.message
        ) }
    }

    fun resetState() {
        _userState.update { SignInState() }
    }


}
