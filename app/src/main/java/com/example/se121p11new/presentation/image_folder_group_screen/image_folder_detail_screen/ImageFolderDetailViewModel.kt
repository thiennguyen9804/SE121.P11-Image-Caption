package com.example.se121p11new.presentation.image_folder_group_screen.image_folder_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.ImageFolder
import com.example.se121p11new.data.local.realm_object.RealmImage
import com.example.se121p11new.data.local.realm_object.RealmImageFolder
import com.example.se121p11new.domain.repository.ImageFolderRepository
import com.example.se121p11new.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class ImageFolderDetailViewModel @Inject constructor(
    private val imageFolderRepository: ImageFolderRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {
    private var _imageFolder = MutableStateFlow(RealmImageFolder())
    val imageFolder = _imageFolder.asStateFlow()
    private val _imageFolderList = MutableStateFlow<List<ImageFolder>>(emptyList())
    val imageFolderList = _imageFolderList
        .onStart { getAllImageFolder() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private fun getAllImageFolder() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageFolderRepository.getAllFoldersLocally().collectLatest {
                    _imageFolderList.value = it.list.toList()
                }
            }
        }
    }

    suspend fun getImageFolderId(id: ObjectId) {
        imageFolderRepository.getImageFolderById(id).collectLatest {
            _imageFolder.value = it.list.toList().first()
        }
    }

    fun deleteImage(image: RealmImage) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageRepository.deleteImageLocally(image)
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