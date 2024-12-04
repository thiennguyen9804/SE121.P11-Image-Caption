package com.example.se121p11new.data.repository

import com.example.se121p11new.data.local.LocalImageFolderDataSource
import com.example.se121p11new.data.local.realm_object.ImageFolder
import com.example.se121p11new.data.local.realm_object.RealmImageFolder
import com.example.se121p11new.domain.repository.ImageFolderRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageFolderRepositoryImpl @Inject constructor(
    private val localImageFolderDataSource: LocalImageFolderDataSource
) : ImageFolderRepository {
    override suspend fun addFolder(newFolder: RealmImageFolder) {
        localImageFolderDataSource.createFolder(newFolder)
    }

    override suspend fun getAllFoldersLocally(): Flow<ResultsChange<ImageFolder>> {
        return localImageFolderDataSource.getAllImageFolder()
    }
}
