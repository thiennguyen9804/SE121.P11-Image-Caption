package com.example.se121p11new.data.local

import com.example.se121p11new.data.local.dao.ImageDao
import com.example.se121p11new.data.local.dao.VocabularyDao
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.Vocabulary
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalImageDataSource @Inject constructor(
    private val imageDao: ImageDao,
) {
    suspend fun addImage(newImage: Image): Flow<ObjectChange<Image>> {
        return imageDao.addImage(newImage)
    }

    fun getAllImages(): Flow<ResultsChange<Image>> {
        return imageDao.getAllImages()
    }

    fun getFirstNImage(n: Int): Flow<ResultsChange<Image>> {
        return imageDao.getFirstNImage(n)
    }



}