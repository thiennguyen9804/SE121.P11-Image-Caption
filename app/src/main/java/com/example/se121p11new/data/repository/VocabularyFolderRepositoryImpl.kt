package com.example.se121p11new.data.repository

import com.example.se121p11new.data.local.LocalVocabularyFolderDataSource
import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import com.example.se121p11new.data.local.realm_object.VocabularyFolder
import com.example.se121p11new.data.remote.RemoteVocabularyFolderDataSource
//import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmVocabulary
//import com.example.se121p11new.domain.data.DomainVocabularyFolder
import com.example.se121p11new.domain.repository.VocabularyFolderRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class VocabularyFolderRepositoryImpl @Inject constructor(
    private val localVocabularyFolderDataSource: LocalVocabularyFolderDataSource,
    private val remoteVocabularyFolderDataSource: RemoteVocabularyFolderDataSource,
) : VocabularyFolderRepository {
    override suspend fun getAllFoldersLocally(): Flow<ResultsChange<VocabularyFolder>> {
        return localVocabularyFolderDataSource.getAllImageFolder()
    }

    override suspend fun addFolder(newFolder: VocabularyFolder) {
        localVocabularyFolderDataSource.addFolder(newFolder)
    }

    override suspend fun getVocabularyFolderById(id: ObjectId): Flow<ResultsChange<RealmVocabularyFolder>> {
        return localVocabularyFolderDataSource.getVocabularyFolderById(id)
    }

    override suspend fun addVocabularyToFolder(
        vocabulary: RealmVocabulary,
        folder: RealmVocabularyFolder
    ) {
        localVocabularyFolderDataSource.addVocabularyToFolder(vocabulary, folder)
    }

    override suspend fun removeVocabularyOutOfFolder(
        vocabulary: RealmVocabulary,
        folder: RealmVocabularyFolder
    ) {
        localVocabularyFolderDataSource.removeVocabularyOutOfFolder(vocabulary, folder)
    }

    override suspend fun uploadVocabularyFolder(
        userId: String,
        vocabularyFolder: HashMap<String, Any>
    ) {
        remoteVocabularyFolderDataSource.uploadVocabularyFolderToCloud(userId, vocabularyFolder)
    }


}