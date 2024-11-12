package com.example.se121p11new.domain

import android.content.res.Resources
import android.graphics.Bitmap

interface ImageRepository {
    fun analyzeImage(image: Bitmap): Resources
}