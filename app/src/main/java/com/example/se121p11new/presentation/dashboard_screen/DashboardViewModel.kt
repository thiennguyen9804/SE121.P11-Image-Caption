package com.example.se121p11new.presentation.dashboard_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.ImageFolder
import com.example.se121p11new.domain.repository.ImageFolderRepository
import com.example.se121p11new.domain.repository.ImageRepository
import com.example.se121p11new.domain.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val imageFolderRepository: ImageFolderRepository,
    private val vocabularyRepository: VocabularyRepository,
) : ViewModel() {
    private val _imageFolderList = MutableStateFlow<List<ImageFolder>>(emptyList())
    val images = imageRepository
        .getFirstNImage(3)
        .map {
            it.list.toList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val vocabularies = vocabularyRepository
        .getFirstNSavedVocabularies(4)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val imageFolderList = _imageFolderList
        .onStart { getAllImageFolder() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun deleteImage(image: Image) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageRepository.deleteImageLocally(image)
            }
        }
    }
    private fun getAllImageFolder() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageFolderRepository.getAllFoldersLocally().collectLatest {
                    _imageFolderList.value = it.list.toList()
                }
            }
        }
    }

    fun addImageToFolder(image: Image, folder: ImageFolder) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(!image.imageList.contains(folder)) {
                    imageFolderRepository.addImageToFolder(image, folder)
                }
            }

        }
    }

    fun removeImageOutOfFolder(image: Image, folder: ImageFolder) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(image.imageList.contains(folder)) {
                    imageFolderRepository.removeImageOutOfFolder(image, folder)
                }
            }

        }
    }
}