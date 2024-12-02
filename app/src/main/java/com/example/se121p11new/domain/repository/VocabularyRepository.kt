package com.example.se121p11new.domain.repository

import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Vocabulary
import com.example.se121p11new.data.remote.dto.DomainVocabulary
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface VocabularyRepository {
    suspend fun addVocabulary(newVocabulary: Vocabulary)
    suspend fun getAllVocabularies(): Flow<List<DomainVocabulary>>
    suspend fun getVocabularyByEngVocab(engVocab: String): Flow<Resource<DomainVocabulary>>

}