package com.example.se121p11new.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.se121p11new.data.source.LocalImageDataSource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.RealmImage
import com.example.se121p11new.data.source.RemoteImageDataSource
import com.example.se121p11new.domain.repository.ImageRepository
import com.google.android.gms.tasks.OnCompleteListener
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val remoteImageDataSource: RemoteImageDataSource,
    private val localImageDataSource: LocalImageDataSource,
) : ImageRepository {

    override suspend fun getImageByUriLocally(uri: Uri): Flow<ResultsChange<Image>> {
        return localImageDataSource.getImageByUri(uri)
    }

    override suspend fun generateEnglishText(image: Bitmap): String {
        return remoteImageDataSource.generateEnglishText(image)
//        delay(3000)
//        return "Hello"
    }

    override suspend fun generateVietnameseText(englishText: String): String {
        return remoteImageDataSource.generateVietnameseText(englishText)
//        delay(3000)
//        return "Xin chào"
    }

    override suspend fun addImageLocally(newImage: Image) {
        localImageDataSource.addImage(newImage)
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

    override suspend fun updateVietnameseText(image: RealmImage, newVietnameseText: String) {
        localImageDataSource.updateVietnameseText(image, newVietnameseText)
    }

    override suspend fun updateEnglishText(image: RealmImage, newEnglishText: String) {
        localImageDataSource.updateEnglishText(image, newEnglishText)
    }

    override suspend fun uploadFileToCloud(
        userId: String,
        path: String,
        onCompleteListener: OnCompleteListener<Uri>
    ) {
        remoteImageDataSource.uploadFileToCloud(userId, path, onCompleteListener)
    }


    override suspend fun uploadImageToCloud(
        userId: String,
        image: HashMap<String, String>
    ) {
        remoteImageDataSource.uploadImageToCloud(userId, image)
    }


}