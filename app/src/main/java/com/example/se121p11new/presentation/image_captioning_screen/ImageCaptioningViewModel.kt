package com.example.se121p11new.presentation.image_captioning_screen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.remote.dto.VietnameseTextResponseDto
import com.example.se121p11new.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    var imageUri = ""
    var imageName = ""

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
                    withContext(Dispatchers.IO) {
                        writeImageLocally(
                            imageUri,
                            _generatedEnglishText.value.data!!,
                            _generatedVietnameseText.value.data!!,
                            imageName
                        )
                    }
                }
                else -> Resource.Loading<String>()
            }
        }
    }

    private suspend fun writeImageLocally(
        imageUri: String,
        _englishText: String,
        _vietnameseText: String,
        _imageName: String,
    ) {
        imageRepository.addImageLocally(
            Image().apply {
                pictureUri = imageUri
                englishText = _englishText
                vietnameseText = _vietnameseText
                imageName = _imageName
            }
        )
    }
}