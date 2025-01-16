package com.example.se121p11new.presentation.auth_group_screen.auth_client

//import com.example.se121p11new.presentation.SignInResult
import android.util.Log
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.presentation.auth_group_screen.UserData
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

open class AuthClient {
    protected val auth = Firebase.auth
    open var providerType: String = "None"
    open var credential: AuthCredential? = null

    init {
        auth.setLanguageCode("vi")
    }

    open suspend fun signOut() {
        auth.signOut()
    }

    fun getSignInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    suspend fun signInFromCredential(credential: AuthCredential): Resource<UserData> {
        return try {
            val user = auth.signInWithCredential(credential).await().user
            if(user != null) {
                Resource.Success(
                    data = user.run {
                        UserData(
                            userId = uid,
                            username = displayName,
                            profilePictureUrl = photoUrl.toString()
                        )
                    }
                )
            } else {
                Resource.Error("Cannot get user for some reason")
            }
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            Resource.Error(
                message = e.message ?: "Unknown error"
            )
        }
    }

    fun sendPasswordResetEmail(
        email: String,
        onCompleteListener: (task: Task<Void>) -> Unit
    ) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(onCompleteListener)

    }
}