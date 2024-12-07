package com.example.se121p11new.presentation.vocabulary_folder_group_screen.vocabulary_folder_dashboard_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import com.example.se121p11new.domain.repository.VocabularyFolderRepository
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
class VocabularyFolderDashboardViewModel @Inject constructor(
    private val vocabularyFolderRepository: VocabularyFolderRepository
) : ViewModel() {
    private val _vocabularyFolders = MutableStateFlow<List<RealmVocabularyFolder>>(emptyList())
    val vocabularyFolders = _vocabularyFolders
        .onStart { getAllImageFolder() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private fun getAllImageFolder() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyFolderRepository.getAllFoldersLocally().collectLatest {
                    _vocabularyFolders.value = it.list.toList()
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
}