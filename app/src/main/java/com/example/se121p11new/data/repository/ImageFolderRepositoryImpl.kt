package com.example.se121p11new.data.repository

import com.example.se121p11new.data.local.LocalImageFolderDataSource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.ImageFolder
import com.example.se121p11new.data.local.realm_object.RealmImageFolder
import com.example.se121p11new.domain.repository.ImageFolderRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
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

    override suspend fun getImageFolderById(id: ObjectId): Flow<ResultsChange<ImageFolder>> {
        return localImageFolderDataSource.getImageFolderById(id)
    }

    override suspend fun addImageToFolder(image: Image, folder: ImageFolder) {
        localImageFolderDataSource.addImageToFolder(image, folder)
    }

    override suspend fun removeImageOutOfFolder(image: Image, folder: ImageFolder) {
        localImageFolderDataSource.removeImageOutOfFolder(image, folder)
    }

    override suspend fun deleteFolder(folder: ImageFolder) {
        localImageFolderDataSource.deleteFolder(folder)
    }
}
