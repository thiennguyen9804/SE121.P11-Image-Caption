package com.example.se121p11new.presentation.captured_image_preview_screen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CapturedImagePreviewViewModel @Inject constructor(

) : ViewModel() {
    fun submitImage(bitmap: Bitmap) {

    }
}