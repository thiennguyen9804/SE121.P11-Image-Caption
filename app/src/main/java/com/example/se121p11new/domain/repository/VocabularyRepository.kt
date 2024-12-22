package com.example.se121p11new.domain.repository

import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Vocabulary
//import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmVocabulary
//import com.example.se121p11new.domain.data.DomainVocabularyFolder
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface VocabularyRepository {
    suspend fun addVocabulary(newVocabulary: Vocabulary)
    suspend fun getAllVocabularies(): Flow<List<RealmVocabulary>>
    fun getFirstNSavedVocabularies(n: Int): Flow<List<RealmVocabulary>>
    suspend fun getAllVocabulariesAndReturnRealmObject(): Flow<ResultsChange<RealmVocabulary>>
    suspend fun getVocabularyByEngVocabLocally(engVocab: String): Flow<RealmVocabulary?>
    suspend fun getVocabularyByEngVocabRemotely(engVocab: String): Flow<RealmVocabulary>
    suspend fun deleteVocabularyLocally(vocabulary: Vocabulary)
    suspend fun getVocabularyByEngVocab(engVocab: String): Flow<RealmVocabulary>
    suspend fun clearCache()
    suspend fun updateVocabulary(engVocab: String, newVocabulary: Vocabulary)
    suspend fun uploadVocabulary(userId: String, vocabulary: HashMap<String, Any>)
}