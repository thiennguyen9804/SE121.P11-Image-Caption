package com.example.se121p11new.presentation.vocabulary_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.remote.dto.RealmDefinition
import com.example.se121p11new.data.remote.dto.RealmPartOfSpeech
import com.example.se121p11new.data.remote.dto.RealmPhrasalVerb
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import com.example.se121p11new.domain.data.Vocabulary
import com.example.se121p11new.domain.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VocabularyDetailViewModel @Inject constructor(
    private val vocabularyRepository: VocabularyRepository
) : ViewModel() {
    private val _vocabulary: MutableStateFlow<Resource<Vocabulary>> = MutableStateFlow(Resource.Loading())
    val vocabulary = _vocabulary.asStateFlow()

    fun clearCache() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.clearCache()
            }
        }
    }

    fun getVocabulary(engVocab: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                println("vocab repo: searching for eng vocab...")
                vocabularyRepository.getVocabularyByEngVocab(engVocab).collectLatest { value ->
                    println("vocab repo: value found!!!")
                    _vocabulary.value = value
                }
            }
        }
    }

}