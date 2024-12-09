package com.example.se121p11new.presentation.image_captioning_screen

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.core.presentation.utils.StringFromTime
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.RealmImage
import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import com.example.se121p11new.domain.repository.ImageRepository
import com.example.se121p11new.domain.repository.VocabularyRepository
import com.example.se121p11new.presentation.dashboard_screen.image
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.ext.copyFromRealm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ImageCaptioningViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val vocabularyRepository: VocabularyRepository
) : ViewModel() {
    private val TAG = "ImageCaptioningViewModel"
    private val _generatedEnglishText = MutableStateFlow<Resource<String>>(Resource.Loading())
    val generatedEnglishText = _generatedEnglishText.asStateFlow()
    private val _generatedVietnameseText = MutableStateFlow<Resource<String>>(Resource.Loading())
    val generatedVietnameseText = _generatedVietnameseText.asStateFlow()
    private val _imageName = MutableStateFlow<Resource<String>>(Resource.Loading())
    val imageName = _imageName.asStateFlow()
    private val _captureTime = MutableStateFlow<Resource<String>>(Resource.Loading())
    val captureTime = _captureTime.asStateFlow()
    lateinit var bitmap: Bitmap
        private set
    private var apiTurnOn = true
    private lateinit var imageUri: Uri
    private var realmImage: RealmImage? = null

    fun getRealmImage() {
        viewModelScope.launch {
            imageRepository.getImageByUriLocally(imageUri).collect { image ->
                if (image.list.firstOrNull() != null) {
                    realmImage = image.list.first()
                } else {
                    realmImage = Image().apply {
                        this.imageName = StringFromTime.buildPictureName()
                        this.pictureUri = imageUri.toString()
                        this.captureTime = StringFromTime.buildCaptureTime()
                        this.englishText = ""
                        this.vietnameseText = ""
                    }
                    imageRepository.addImageLocally(realmImage!!)
                }
            }
        }
    }

//    var imageName = StringFromTime.buildPictureName()
//        private set

    fun setMyImageUri(newImageUri: Uri) {
        imageUri = newImageUri
    }


    fun setMyBitmap(newBitmap: Bitmap) {
        bitmap = newBitmap
    }

//    private fun generateEngText(bitmap: Bitmap) {
//        viewModelScope.launch {
//            imageRepository.generateEnglishText(bitmap).collectLatest { eng ->
//                when (eng) {
//                    is Resource.Error -> {}
//                    is Resource.Loading -> {}
//                    is Resource.Success -> {
//                        val value = eng.data!!
//                        _generatedEnglishText.value = Resource.Success(value)
//                        if(_generatedVietnameseText.value is Resource.Loading) {
//                            generateVieText(value)
//                        }
//                        realmImage!!.englishText = value
//
//                        imageRepository.addImageLocally(realmImage!!)
//                    }
//                }
//
//            }
//        }
//
//    }

//        private fun generateVieText(engText: String) {
//            viewModelScope.launch {
//                imageRepository.generateVietnameseText(engText).collectLatest { vie ->
//                    when (vie) {
//                        is Resource.Error -> {}
//                        is Resource.Loading -> {}
//                        is Resource.Success -> {
//                            val decodedVieText =
//                                HtmlCompat.fromHtml(vie.data!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
//                                    .toString()
//                            _generatedVietnameseText.value = Resource.Success(decodedVieText)
//                            realmImage!!.vietnameseText = decodedVieText
//                            delay(5000)
//                            imageRepository.addImageLocally(realmImage!!)
//                        }
//                    }
//                }
//            }
//        }

    fun getImageByUri() {
        _imageName.value = Resource.Success(realmImage!!.imageName)
        _captureTime.value = Resource.Success(realmImage!!.captureTime)
        if (!apiTurnOn || _generatedVietnameseText.value is Resource.Success) {
            return
        }

        if (realmImage!!.englishText != "" && realmImage!!.vietnameseText != "") {
            _generatedEnglishText.value = Resource.Success(realmImage!!.englishText)
            _generatedVietnameseText.value = Resource.Success(realmImage!!.vietnameseText)
        } else if (realmImage!!.englishText != "" && realmImage!!.vietnameseText == "") {
            viewModelScope.launch {
                val engText = realmImage!!.englishText
                _generatedEnglishText.value = Resource.Success(realmImage!!.englishText)
                val vieText = async { imageRepository.generateVietnameseText(engText) }.await()
                _generatedVietnameseText.value = Resource.Success(vieText)
    //                realmImage!!.vietnameseText = vieText
    //                imageRepository.addImageLocally(realmImage!!)
                imageRepository.updateVietnameseText(realmImage!!, vieText)
            }
        } else if (realmImage!!.englishText == "" && realmImage!!.vietnameseText == "") {
//                if(_generatedEnglishText.value is Resource.Success) {
//                    generateVieText(_generatedEnglishText.value.data!!)
//                }

            viewModelScope.launch {
                val engText = async { imageRepository.generateEnglishText(bitmap) }.await()
//                realmImage!!.englishText = engText
//                imageRepository.addImageLocally(realmImage!!)
                imageRepository.updateEnglishText(realmImage!!, engText)
                _generatedEnglishText.value = Resource.Success(engText)
                val vieText = async { imageRepository.generateVietnameseText(engText) }.await()
                _generatedVietnameseText.value = Resource.Success(vieText)
//                realmImage!!.vietnameseText = vieText
//                imageRepository.addImageLocally(realmImage!!)
                imageRepository.updateVietnameseText(realmImage!!, vieText)

            }


        }
    }

    fun saveVocabularyLocally(engVocab: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.getVocabularyByEngVocab(engVocab).collectLatest {
                    when (it) {
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
                    when (it) {
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