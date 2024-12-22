package com.example.se121p11new.presentation.profile_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.core.utils.toMap
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import com.example.se121p11new.domain.repository.ImageFolderRepository
import com.example.se121p11new.domain.repository.ImageRepository
import com.example.se121p11new.domain.repository.VocabularyFolderRepository
import com.example.se121p11new.domain.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val imageFolderRepository: ImageFolderRepository,
    private val vocabularyRepository: VocabularyRepository,
    private val vocabularyFolderRepository: VocabularyFolderRepository,
) : ViewModel() {
    private val TAG = "ProfileViewModel"
    fun uploadToCloud(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            async { uploadImageToCloud(userId)}.await()
            async {uploadImageFolderToCloud(userId)}.await()
            async {uploadVocabulary(userId)}.await()
            async {uploadVocabularyFolder(userId)}.await()
        }
    }

    private suspend fun uploadVocabularyFolder(userId: String) {
        vocabularyFolderRepository.getAllFoldersLocally().map {
            it.list.toList().map { folder ->
                folder.toMap()
            }
        }.collect { allFolder ->
            allFolder.forEach { folder ->
                vocabularyFolderRepository.uploadVocabularyFolder(userId, folder)
            }
        }
    }

    private suspend fun uploadVocabulary(userId: String) {
        vocabularyRepository.getAllVocabularies().map {
            it.map(RealmVocabulary::toMap)
        }.collect {
            it.forEach { item ->
                vocabularyRepository.uploadVocabulary(userId, item)
            }
        }
    }

    private suspend fun uploadImageFolderToCloud(userId: String) {
        imageFolderRepository.getAllFoldersLocally().map {
            it.list.toList().map { imageFolder ->
                imageFolder.toMap()
            }
        }.collect { allImageFolder ->
            allImageFolder.forEach { imageFolder ->
                imageFolderRepository.uploadImageFolderToCloud(userId, imageFolder)
            }
        }
    }

    private suspend fun uploadImageToCloud(userId: String) {
        imageRepository.getAllImagesLocally().map {
            it.list.toList().map { image ->
                image.toMap()
            }
        }.collect { allImage ->
            allImage.forEach {
                withContext(Dispatchers.IO) {
                    imageRepository.uploadFileToCloud(
                        userId,
                        it["pictureUri"]!!,
                        onCompleteListener = { uri ->
                            val downloadUri = uri.result
                            it["pictureUri"] = downloadUri.toString()
                            if(it["pictureUri"]!!.isEmpty()) {
                                throw Exception("Cannot get uploaded Image Uri")
                            }
                            viewModelScope.launch {
                                imageRepository.uploadImageToCloud(userId, it)
                            }
                        }
                    )
                }
            }
        }

    }
}