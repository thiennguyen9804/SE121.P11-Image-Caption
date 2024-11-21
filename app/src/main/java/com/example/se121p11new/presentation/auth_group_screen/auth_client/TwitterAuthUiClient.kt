package com.example.se121p11new.presentation.auth_group_screen.auth_client

import android.app.Activity
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.presentation.auth_group_screen.SignInResult
import com.example.se121p11new.presentation.auth_group_screen.UserData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class TwitterAuthUiClient(
    private val activity: Activity,
) : AuthClient() {
    private val twitterProvider = OAuthProvider.newBuilder("twitter.com").apply {
        addCustomParameter("lang", "en")
    }
    suspend fun signInWithPendingResult(): Resource<UserData> {
        return try {
            val pendingResultTask = auth.pendingAuthResult?.await()
            if(pendingResultTask != null) {
                val user = pendingResultTask.user
                if(user != null) {
                    Resource.Success(
                        data = super.getSignInUser()!!,
                    )
                } else {
                    Resource.Error("Cannot get user from pending result")
                }
            } else {
                signIn()
            }
        } catch(e: Exception) {
            if(e is CancellationException) throw e
            Resource.Error("Cannot sign in with Twitter!!!")
        }
    }

    private suspend fun signIn(): Resource<UserData> {
        return try {
            val result = auth.startActivityForSignInWithProvider(activity, twitterProvider.build()).await()
            val user = result.user
            if(user != null) {
                Resource.Success(
                    data = user.run {
                        UserData(
                            userId = uid,
                            username = displayName,
                            profilePictureUrl = photoUrl?.toString()
                        )
                    }
                )
            } else {
                Resource.Error("Cannot get user from result in Twitter")
            }

        } catch(e: Exception) {
            if(e is CancellationException) throw e
            Resource.Error(
                message = e.message!!
            )
        }
    }

}