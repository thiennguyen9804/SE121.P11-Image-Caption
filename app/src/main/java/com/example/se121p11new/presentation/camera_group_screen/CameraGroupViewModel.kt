package com.example.se121p11new.presentation.camera_group_screen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraGroupViewModel @Inject constructor() : ViewModel() {

    lateinit var bitmap: Bitmap

    fun setCapturedBitmap(newBitmap: Bitmap) {
        bitmap = newBitmap
    }

    fun writeBitmapToInternalStorage(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            file.outputStream().use { outputStream ->
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    50,
                    outputStream,
                )
            }
        }
    }
}