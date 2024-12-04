package com.example.se121p11new.presentation.image_folder_dashboard_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.RealmImageFolder
import com.example.se121p11new.domain.repository.ImageFolderRepository
import com.example.se121p11new.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImageFolderDashboardViewModel @Inject constructor(
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

    private fun getAllImages() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageRepository.getAllImagesLocally().collectLatest {
                    _images.value = it.list.toList()
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
}