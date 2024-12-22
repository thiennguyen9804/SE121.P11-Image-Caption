package com.example.se121p11new.domain.repository

import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import com.example.se121p11new.data.local.realm_object.VocabularyFolder
//import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmVocabulary
//import com.example.se121p11new.domain.data.DomainVocabularyFolder
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface VocabularyFolderRepository {
    suspend fun getAllFoldersLocally(): Flow<ResultsChange<VocabularyFolder>>
    suspend fun addFolder(newFolder: RealmVocabularyFolder)
    suspend fun getVocabularyFolderById(id: ObjectId): Flow<ResultsChange<RealmVocabularyFolder>>
    suspend fun addVocabularyToFolder(vocabulary: RealmVocabulary, folder: RealmVocabularyFolder)
    suspend fun removeVocabularyOutOfFolder(vocabulary: RealmVocabulary, folder: RealmVocabularyFolder)
    suspend fun uploadVocabularyFolder(userId: String, vocabularyFolder: HashMap<String, Any>)
}