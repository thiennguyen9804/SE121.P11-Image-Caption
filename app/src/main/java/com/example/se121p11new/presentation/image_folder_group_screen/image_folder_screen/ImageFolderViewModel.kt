package com.example.se121p11new.presentation.image_folder_group_screen.image_folder_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.data.local.realm_object.RealmImageFolder
import com.example.se121p11new.domain.repository.ImageFolderRepository
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
class ImageFolderViewModel @Inject constructor(
    private val imageFolderRepository: ImageFolderRepository
) : ViewModel() {
    private val _imageFolders = MutableStateFlow<List<RealmImageFolder>>(emptyList())
    val imageFolders = _imageFolders
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
                    _imageFolders.value = it.list.toList()
                }
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