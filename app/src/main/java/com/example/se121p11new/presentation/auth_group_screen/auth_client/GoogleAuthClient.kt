package com.example.se121p11new.presentation.auth_group_screen.auth_client

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.presentation.auth_group_screen.SignInResult
import com.example.se121p11new.presentation.auth_group_screen.UserData
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
) : AuthClient() {
    override var providerType: String = "Google"
    override var credential: AuthCredential? = null

    suspend fun buildIntentSender(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }


    suspend fun getSignInResultFromIntent(intent: Intent): Resource<UserData> {
        val signInCredential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = signInCredential.googleIdToken
        credential = GoogleAuthProvider.getCredential(googleIdToken, null)
        val signInResult = super.signInFromCredential(credential!!)
        return signInResult
    }

    override suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}