package com.example.se121p11new.presentation.image_captioning_screen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageCaptioningViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {
    var bitmap: Bitmap? = null
        private set
    var accessToken: String? = null
    private val _generatedEnglishText = MutableStateFlow<Resource<String>>(Resource.Loading())
    val generatedEnglishText = _generatedEnglishText.asStateFlow()
    private val _generatedVietnameseText = MutableStateFlow<Resource<String>>(Resource.Loading())
    val generatedVietnameseText = _generatedVietnameseText.asStateFlow()

    private suspend fun generateEnglishText(bitmap: Bitmap) {
        _generatedEnglishText.value = imageRepository.generateEnglishText(bitmap)
    }

    private suspend fun generateVietnameseText(englishText: String) {
        _generatedVietnameseText.value = imageRepository.generateVietnameseText(englishText)
    }

    fun setBitmap(newBitmap: Bitmap) {
        bitmap = newBitmap
        viewModelScope.launch {
            generateEnglishText(bitmap!!)
            when(_generatedEnglishText.value) {
                is Resource.Success -> {
                    generateVietnameseText(_generatedEnglishText.value.data!!)
                }
                else -> Resource.Loading<String>()
            }
        }


    }
}