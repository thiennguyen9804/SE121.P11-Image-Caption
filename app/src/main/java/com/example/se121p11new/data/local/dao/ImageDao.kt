package com.example.se121p11new.data.local.dao

import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Image
import io.realm.kotlin.Realm

class ImageDao(
    private val realm: Realm
) {
    suspend fun addImage(newImage: Image): Resource<Image> {
        val image = realm.write {
            newImage
        }

        return Resource.Success(image)
    }
}