package com.example.se121p11new.data.local


import com.example.se121p11new.data.local.realm_object.Image
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class LocalImageDataSource @Inject constructor(
    private val realm: Realm
) {
    suspend fun addImage(newImage: Image): Flow<ObjectChange<Image>> {
        val image = realm.write {
            newImage
            copyToRealm(newImage, UpdatePolicy.ALL)
        }.asFlow()
        return image
    }

    fun getAllImages(): Flow<ResultsChange<Image>> {
        val res = realm
            .query<Image>()
            .asFlow()
        return res
    }

    fun getFirstNImage(n: Int): Flow<ResultsChange<Image>> {
        val res = realm
            .query<Image>()
//            .sort("_id.timestamp", Sort.DESCENDING)
            .limit(n)
            .find()
            .asFlow()
        return res
    }

    suspend fun deleteImage(image: Image) {
        val deleted = File(image.pictureUri).delete()
//        if(deleted) {
//
//        }
        realm.write {
            val imageToDelete = query<Image>("_id == $0", image._id).first().find()
            imageToDelete?.let {
                delete(it)
            }
        }
    }

}