package com.example.se121p11new.presentation.vocabulary_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.remote.dto.RealmDefinition
import com.example.se121p11new.data.remote.dto.RealmPartOfSpeech
import com.example.se121p11new.data.remote.dto.RealmPhrasalVerb
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import com.example.se121p11new.domain.data.Vocabulary
import com.example.se121p11new.domain.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VocabularyDetailViewModel @Inject constructor(
    private val vocabularyRepository: VocabularyRepository
) : ViewModel() {
    private val config = RealmConfiguration.Builder(
        schema = setOf(
            RealmVocabulary::class,
            RealmPhrasalVerb::class,
            RealmPartOfSpeech::class,
            RealmDefinition::class
        ))
        .name("cache.realm")
        .deleteRealmIfMigrationNeeded()
        .build()

    private val cache by lazy {
        Realm.open(configuration = config)
    }

    private val _vocabulary: MutableStateFlow<Resource<Vocabulary>> = MutableStateFlow(Resource.Loading())
    val vocabulary = _vocabulary.asStateFlow()

    fun getVocabulary(engVocab: String) {
        viewModelScope.launch {
            cache.query<RealmVocabulary>("engVocab == $0", engVocab).asFlow()
                .collectLatest {
                    val res = it.list.toList()
                    if(res.isEmpty()) {
                        vocabularyRepository.getVocabularyByEngVocab(engVocab).collectLatest { value ->
                            _vocabulary.value = value
                        }
                    } else {
                        println("there's such a word")
                    }
                }
        }
    }

}