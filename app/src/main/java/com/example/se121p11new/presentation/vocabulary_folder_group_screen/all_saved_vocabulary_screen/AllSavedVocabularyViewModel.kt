package com.example.se121p11new.presentation.vocabulary_folder_group_screen.all_saved_vocabulary_screen

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
import javax.inject.Inject

@HiltViewModel
class AllSavedVocabularyViewModel @Inject constructor(
    private val vocabularyRepository: VocabularyRepository,
    private val vocabularyFolderRepository: VocabularyFolderRepository
) : ViewModel() {
    private val _vocabularyFolderList = MutableStateFlow<List<RealmVocabularyFolder>>(emptyList())
    val vocabularyFolderList = _vocabularyFolderList
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

    private fun getAllVocabularyFolder() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyFolderRepository.getAllFoldersLocally().collectLatest {
                    _vocabularyFolderList.value = it.list.toList()
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

    fun deleteVocabulary(vocabulary: RealmVocabulary) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.deleteVocabularyLocally(vocabulary)
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