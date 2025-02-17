package com.example.se121p11new.presentation.auth_group_screen.auth_client

import android.net.Uri
import android.util.Log
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.presentation.auth_group_screen.UserData
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class FacebookAuthClient : AuthClient() {
    private val TAG = "FacebookAuthClient"

    suspend fun handleFacebookAccessToken(token: AccessToken): Resource<UserData> {
        val credential = FacebookAuthProvider.getCredential(token.token)
        val signInResult = super.signInFromCredential(credential)
        val user = auth.currentUser
        Log.d(TAG, user!!.displayName.toString())
        if(!user.photoUrl.toString().contains("access_token")) {
            try {
                val url = buildPictureUrl(user.photoUrl.toString())
                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(url)
                }
                user.updateProfile(profileUpdates)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return signInResult
    }

    override suspend fun signOut() {
        LoginManager.getInstance().logOut()
        super.signOut()
    }

    private fun buildPictureUrl(
        profilePictureUrl: String,
    ): String = "${profilePictureUrl}?access_token=${AccessToken.getCurrentAccessToken()?.token}&height=150"
}