package com.example.se121p11new.domain.repository

import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.ImageFolder
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface ImageFolderRepository {
    suspend fun addFolder(newFolder: ImageFolder)
    suspend fun getAllFoldersLocally(): Flow<ResultsChange<ImageFolder>>
}