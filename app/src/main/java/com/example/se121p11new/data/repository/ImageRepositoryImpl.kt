package com.example.se121p11new.data.repository

import android.graphics.Bitmap
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.LocalImageDataSource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.remote.RemoteImageDataSource
import com.example.se121p11new.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val remoteImageDataSource: RemoteImageDataSource,
    private val localImageDataSource: LocalImageDataSource,
) : ImageRepository {
    override suspend fun generateEnglishText(image: Bitmap): Resource<String> {
        return remoteImageDataSource.generateEnglishText(image)
    }

    override suspend fun generateVietnameseText(englishText: String): Resource<String> {
        return remoteImageDataSource.generateVietnameseText(englishText)
    }

    override suspend fun addImageLocally(newImage: Image) {
        localImageDataSource.addImage(newImage)
    }

}