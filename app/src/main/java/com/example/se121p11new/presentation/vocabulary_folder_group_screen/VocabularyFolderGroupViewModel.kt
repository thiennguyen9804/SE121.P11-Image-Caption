package com.example.se121p11new.presentation.vocabulary_folder_group_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import com.example.se121p11new.data.remote.dto.RealmVocabulary
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
class VocabularyFolderGroupViewModel @Inject constructor(
    private val vocabularyFolderRepository: VocabularyFolderRepository,
    private val vocabularyRepository: VocabularyRepository
) : ViewModel() {
    private val _vocabularyFolders = MutableStateFlow<List<RealmVocabularyFolder>>(emptyList())
    val vocabularyFolders = _vocabularyFolders
        .onStart { getAllVocabularyFolder() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    private val _vocabularyList = MutableStateFlow<List<RealmVocabulary>>(emptyList())
    val vocabularyList = _vocabularyList
        .onStart { getAllVocabularies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    private var _vocabularyFolder = MutableStateFlow(RealmVocabularyFolder())
    val vocabularyFolder = _vocabularyFolder.asStateFlow()

    fun getVocabularyFolderById(id: ObjectId) {
        viewModelScope.launch(Dispatchers.IO) {
            vocabularyFolderRepository.getVocabularyFolderById(id).collectLatest {
                _vocabularyFolder.value = it.list.toList().first()
            }
        }
    }

    private fun getAllVocabularyFolder() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyFolderRepository.getAllFoldersLocally().collectLatest {
                    _vocabularyFolders.value = it.list.toList()
                }
            }
        }
    }

    private fun getAllVocabularies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.getAllVocabulariesAndReturnRealmObject().collectLatest {
                    _vocabularyList.value = it.list.toList()
                }
            }
        }
    }

    fun createFolder(name: String) {
        val folder = RealmVocabularyFolder().apply {
            this.name = name
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyFolderRepository.addFolder(folder)
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
                    removeVocabularyOutOfFolder(vocabulary, folder)

                }
            }
        }
    }

    fun deleteVocabulary(vocabulary: RealmVocabulary) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.deleteVocabularyLocally(vocabulary)
            }
        }
    }

}