package com.example.se121p11new.presentation.profile_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.domain.repository.ImageFolderRepository
import com.example.se121p11new.domain.repository.ImageRepository
import com.example.se121p11new.domain.repository.VocabularyFolderRepository
import com.example.se121p11new.domain.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val imageFolderRepository: ImageFolderRepository,
    private val vocabularyRepository: VocabularyRepository,
    private val vocabularyFolderRepository: VocabularyFolderRepository,
//    private val fireStoreDb
) : ViewModel() {
    private val TAG = "ProfileViewModel"
    fun uploadToCloud(userId: String) {
        viewModelScope.launch {
            imageRepository.getAllImagesLocally().map {
                it.list.toList()
            }.collect { allImage ->
//                Log.d(TAG, "uploadToCloud: ${allImage.size}")
            }
        }
    }
}