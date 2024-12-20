package com.example.se121p11new.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.LocalImageDataSource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.RealmImage
import com.example.se121p11new.data.remote.RemoteImageDataSource
import com.example.se121p11new.domain.repository.ImageRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
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