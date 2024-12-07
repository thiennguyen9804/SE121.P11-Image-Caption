package com.example.se121p11new.presentation.vocabulary_folder_group_screen.vocabulary_folder_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import com.example.se121p11new.domain.data.DomainVocabularyFolder
import com.example.se121p11new.domain.repository.VocabularyFolderRepository
import com.example.se121p11new.domain.repository.VocabularyRepository
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
class VocabularyFolderDetailViewModel @Inject constructor(
    private val vocabularyFolderRepository: VocabularyFolderRepository,
    private val vocabularyRepository: VocabularyRepository
) : ViewModel() {
    private var _vocabularyFolder = MutableStateFlow(RealmVocabularyFolder())
    val vocabularyFolder = _vocabularyFolder.asStateFlow()
    private val _vocabularyFolderList = MutableStateFlow<List<RealmVocabularyFolder>>(emptyList())
    val vocabularyFolderList = _vocabularyFolderList
        .onStart { getAllVocabularyFolder() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private fun getAllVocabularyFolder() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyFolderRepository.getAllFoldersLocally().collectLatest {
                    _vocabularyFolderList.value = it.list.toList()
                }
            }
        }
    }

    suspend fun getVocabularyFolderId(id: ObjectId) {
        vocabularyFolderRepository.getVocabularyFolderById(id).collectLatest {
            _vocabularyFolder.value = it.list.toList().first()
        }
    }

    fun deleteVocabulary(vocabulary: RealmVocabulary) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.deleteVocabularyLocally(vocabulary)
            }
        }
    }

    fun addVocabularyToFolder(vocabulary: RealmVocabulary, folder: RealmVocabularyFolder) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(!vocabulary.vocabularyList.contains(folder)) {
                    vocabularyFolderRepository.addVocabularyToFolder(
                        vocabulary,
                        folder
                    )
                }
            }

        }
    }

    fun removeVocabularyOutOfFolder(vocabulary: RealmVocabulary, folder: RealmVocabularyFolder) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(vocabulary.vocabularyList.contains(folder)) {
                    vocabularyFolderRepository.
                        removeVocabularyOutOfFolder(
                            vocabulary,
                            folder
                        )

                }
            }
        }
    }

}