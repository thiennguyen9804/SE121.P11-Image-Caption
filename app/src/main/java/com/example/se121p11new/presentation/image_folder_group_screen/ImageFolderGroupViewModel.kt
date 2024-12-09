package com.example.se121p11new.presentation.image_folder_group_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.ImageFolder
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
class ImageFolderGroupViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val imageFolderRepository: ImageFolderRepository
) : ViewModel() {
    private val _images = MutableStateFlow<List<Image>>(emptyList())
    val images = _images
        .onStart { getAllImages() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _imageFolderList = MutableStateFlow<List<ImageFolder>>(emptyList())
    val imageFolderList = _imageFolderList
        .onStart { getAllImageFolder() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    private val _folder = MutableStateFlow(RealmImageFolder())
    val folder = _folder.asStateFlow()

    private fun getAllImageFolder() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageFolderRepository.getAllFoldersLocally().collectLatest {
                    _imageFolderList.value = it.list.toList()
                }
            }
        }
    }

    private fun getAllImages() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageRepository.getAllImagesLocally().collectLatest {
                    _images.value = it.list.toList()
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

    fun deleteImage(image: Image) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageRepository.deleteImageLocally(image)
            }
        }
    }

    fun getFolderById(id: ObjectId) {
        viewModelScope.launch(Dispatchers.IO) {
            imageFolderRepository.getImageFolderById(id).collectLatest {
                _folder.value = it.list.first()
            }
        }
    }

    fun createFolder(name: String) {
        val folder = RealmImageFolder().apply {
            this.name = name
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageFolderRepository.addFolder(folder)
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

    fun deleteFolder(folder: ImageFolder) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageFolderRepository.deleteFolder(folder)
            }
        }
    }
}