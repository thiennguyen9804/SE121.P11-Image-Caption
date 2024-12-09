package com.example.se121p11new.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.RealmImage
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getImageByUriLocally(uri: Uri): Flow<ResultsChange<Image>>
//    suspend fun getImageByUri(uri: Uri): Flow<ResultsChange<Image>>
    suspend fun generateEnglishText(image: Bitmap): String
    suspend fun generateVietnameseText(englishText: String): String
    suspend fun addImageLocally(newImage: Image)
    fun getAllImagesLocally(): Flow<ResultsChange<Image>>
    fun getFirstNImage(n: Int): Flow<ResultsChange<Image>>
    suspend fun deleteImageLocally(image: Image)
    suspend fun updateVietnameseText(image: RealmImage, newVietnameseText: String)
    suspend fun updateEnglishText(image: RealmImage, newEnglishText: String)
}