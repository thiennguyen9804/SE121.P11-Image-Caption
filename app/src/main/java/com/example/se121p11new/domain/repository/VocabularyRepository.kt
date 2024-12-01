package com.example.se121p11new.domain.repository

import com.example.se121p11new.data.local.realm_object.Vocabulary
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface VocabularyRepository {
    suspend fun addVocabulary(newVocabulary: Vocabulary): Flow<ObjectChange<Vocabulary>>
    fun getAllVocabularies(): Flow<ResultsChange<Vocabulary>>
    fun getVocabularyByEngVocab(engVocab: String): Flow<ResultsChange<Vocabulary>>

}