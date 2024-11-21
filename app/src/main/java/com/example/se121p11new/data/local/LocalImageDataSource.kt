package com.example.se121p11new.data.local

import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.dao.ImageDao
import com.example.se121p11new.data.local.realm_object.Image

class LocalImageDataSource(
    private val imageDao: ImageDao
) {
    suspend fun addImage(newImage: Image): Resource<Image> {
        return imageDao.addImage(newImage)
    }

    suspend fun getAllIma
}