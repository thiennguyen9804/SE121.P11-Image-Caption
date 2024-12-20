package com.example.se121p11new.presentation.profile_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.core.utils.toFirebaseImage
import com.example.se121p11new.domain.repository.ImageFolderRepository
import com.example.se121p11new.domain.repository.ImageRepository
import com.example.se121p11new.domain.repository.VocabularyFolderRepository
import com.example.se121p11new.domain.repository.VocabularyRepository
import com.example.se121p11new.presentation.image_folder_group_screen.image_folder_dashboard_screen.image
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
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
            uploadImageToCloud(userId)

        }
    }

    private suspend fun uploadImageFolderToCloud(userId: String) {
//        imageFolderRepository.getAllFoldersLocally().map {
//            it.list.toList().map { imageFolder ->
//                imageFolder.toFirebaseImage()
//            }
//        }.collect { allImageFolder ->
//        }
    }

    private suspend fun uploadImageToCloud(userId: String) {
        imageRepository.getAllImagesLocally().map {
            it.list.toList().map { image ->
                image.toFirebaseImage()
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