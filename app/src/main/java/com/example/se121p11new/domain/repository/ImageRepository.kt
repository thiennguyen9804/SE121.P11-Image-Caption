package com.example.se121p11new.domain.repository

import android.graphics.Bitmap
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Image

interface ImageRepository {
    suspend fun generateEnglishText(image: Bitmap): Resource<String>
    suspend fun generateVietnameseText(englishText: String): Resource<String>
    suspend fun addImageLocally(newImage: Image)
}