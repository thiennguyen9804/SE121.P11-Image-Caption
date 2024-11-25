package com.example.se121p11new.data

import android.graphics.Bitmap
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.domain.repository.ImageRepository
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockImageRepostiory : ImageRepository {
    override suspend fun generateEnglishText(image: Bitmap): Flow<Resource<String>> {
        return flow {
            Resource.Success("Hello")
        }
    }

    override suspend fun generateVietnameseText(englishText: String): Flow<Resource<String>> {
        return flow {
            Resource.Success("Xin chào")
        }
    }

    override suspend fun addImageLocally(newImage: Image): Flow<ObjectChange<Image>> {
        return flow {}
    }

    override fun getAllImagesLocally(): Flow<ResultsChange<Image>> {
        return flow {}
    }

    override fun getFirstNImage(n: Int): Flow<ResultsChange<Image>> {
        return flow {}
    }

}