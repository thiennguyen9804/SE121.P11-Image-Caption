package com.example.se121p11new.data.repository

import android.graphics.Bitmap
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.LocalImageDataSource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.remote.RemoteImageDataSource
import com.example.se121p11new.domain.repository.ImageRepository
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val remoteImageDataSource: RemoteImageDataSource,
    private val localImageDataSource: LocalImageDataSource,
) : ImageRepository {
    override suspend fun generateEnglishText(image: Bitmap): Flow<Resource<String>> {
        return remoteImageDataSource.generateEnglishText(image)
    }

    override suspend fun generateVietnameseText(englishText: String): Flow<Resource<String>> {
        return remoteImageDataSource.generateVietnameseText(englishText)
    }

    override suspend fun addImageLocally(newImage: Image): Flow<ObjectChange<Image>> {
        return localImageDataSource.addImage(newImage)
    }

    override fun getAllImagesLocally(): Flow<ResultsChange<Image>> {
        return localImageDataSource.getAllImages()
    }

    override fun getFirstNImage(n: Int): Flow<ResultsChange<Image>> {
        return localImageDataSource.getFirstNImage(n)
    }

    override suspend fun deleteImageLocally(image: Image) {
        return localImageDataSource.deleteImage(image)
    }
}