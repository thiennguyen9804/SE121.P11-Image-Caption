package com.example.se121p11new.presentation.image_captioning_screen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import com.example.se121p11new.domain.repository.ImageRepository
import com.example.se121p11new.domain.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImageCaptioningViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val vocabularyRepository: VocabularyRepository
) : ViewModel() {
    private val _generatedEnglishText = MutableStateFlow<Resource<String>>(Resource.Loading())
    val generatedEnglishText = _generatedEnglishText.asStateFlow()
    private val _generatedVietnameseText = MutableStateFlow<Resource<String>>(Resource.Loading())
    val generatedVietnameseText = _generatedVietnameseText.asStateFlow()
    lateinit var bitmap: Bitmap
        private set
    var apiTurnOn = false
    var imageUri = ""
    var imageName = ""
    var captureTime = ""

    fun generateText(newBitmap: Bitmap) {
        if(!apiTurnOn || _generatedVietnameseText.value is Resource.Success) {
            return
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageRepository.generateEnglishText(newBitmap).collectLatest { engText ->
                    when(engText) {
                        is Resource.Success -> {
                            _generatedEnglishText.emit(Resource.Success(engText.data!!))
                            imageRepository.generateVietnameseText(engText.data).collectLatest { vieText ->
                                when(vieText) {
                                    is Resource.Success -> {
                                        _generatedVietnameseText.value = (Resource.Success(vieText.data!!))
                                        imageRepository.addImageLocally(
                                            Image().apply {
                                                this.imageName = this@ImageCaptioningViewModel.imageName
                                                this.pictureUri = this@ImageCaptioningViewModel.imageUri
                                                this.englishText = engText.data
                                                this.vietnameseText = vieText.data
                                                this.captureTime = this@ImageCaptioningViewModel.captureTime
                                            }
                                        )
                                        apiTurnOn = false
                                    }
                                    is Resource.Error -> _generatedVietnameseText.value = (Resource.Error("Cannot translate to Vietnamese"))
                                    is Resource.Loading -> {}
                                }
                            }
                        }
                        is Resource.Error -> _generatedEnglishText.emit(Resource.Error("Cannot generate English"))
                        is Resource.Loading -> {}
                    }
                }
            }
        }

    }

    fun saveVocabularyLocally(engVocab: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.getVocabularyByEngVocab(engVocab).collectLatest {
                    when(it) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            vocabularyRepository.addVocabulary(it.data!!.toRealmVocabulary())
                        }
                    }
                }
            }
        }
    }

    fun deleteVocabularyLocally(engVocab: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.getVocabularyByEngVocabLocally(engVocab).collectLatest {
                    when(it) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            vocabularyRepository.deleteVocabularyLocally(it.data!!.toRealmVocabulary())
                        }
                    }
                }
            }
        }
    }
}