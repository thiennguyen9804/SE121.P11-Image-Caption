package com.example.se121p11new.presentation.auth_group_screen.auth_client

import android.net.Uri
import com.example.se121p11new.core.data.AppConstants
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.presentation.auth_group_screen.UserData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class EmailAuthClient : AuthClient() {
    override var providerType: String = "Email"
    override var credential: AuthCredential? = null
    suspend fun signInWithEmail(email: String, password: String): Resource<UserData> {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).await().user
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
    suspend fun signUp(email: String, password: String, userName: String): Resource<UserData> {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user
            if(user != null) {
                val profileUpdates = userProfileChangeRequest {
                    displayName = userName
                    photoUri = Uri.parse(AppConstants.DEFAULT_AVATAR)
                }
                user.updateProfile(profileUpdates)
                return Resource.Success(
                    data = user.run {
                        UserData(
                            userId = uid,
                            username = userName,
                            profilePictureUrl = AppConstants.DEFAULT_AVATAR
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
}
