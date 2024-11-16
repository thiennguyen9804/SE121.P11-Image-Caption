package com.example.se121p11new.domain.repository

import android.graphics.Bitmap
import com.example.se121p11new.core.presentation.utils.Resource

interface ImageRepository {
    suspend fun generateEnglishText(image: Bitmap): Resource<String>
    suspend fun generateVietnameseText(englishText: String): Resource<String>
}