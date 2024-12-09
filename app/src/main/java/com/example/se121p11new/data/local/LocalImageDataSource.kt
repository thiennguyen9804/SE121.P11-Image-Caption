package com.example.se121p11new.data.local


import android.net.Uri
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.RealmImage
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class LocalImageDataSource @Inject constructor(
    private val realm: Realm
) {
    // upsert function
    suspend fun addImage(newImage: Image) {
        val image = realm.writeBlocking {
            copyToRealm(newImage, UpdatePolicy.ALL)
        }.asFlow()
//        return image
//        realm.write {
//            val image = query<Image>("_id == $0", newImage._id).find().firstOrNull()
////            if(image != null) {
////            }
//            if(image != null) {
//                copyToRealm(Image().apply {
//                    _id = image._id
//                    imageName = newImage.imageName
//                    pictureUri = newImage.pictureUri
//                    captureTime = newImage.captureTime
//                    englishText = newImage.englishText
//                    vietnameseText = newImage.vietnameseText
//                })
//            } else {
//                copyToRealm(Image().apply {
//                    imageName = newImage.imageName
//                    pictureUri = newImage.pictureUri
//                    captureTime = newImage.captureTime
//                    englishText = newImage.englishText
//                    vietnameseText = newImage.vietnameseText
//                })
//            }
//        }
    }

    fun getAllImages(): Flow<ResultsChange<Image>> {
        val res = realm
            .query<Image>()
            .find()
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
        File(image.pictureUri).deleteOnExit()
        realm.write {
            val imageToDelete = query<Image>("_id == $0", image._id).first().find() ?: return@write
            delete(imageToDelete)
        }
    }

    fun getImageByUri(uri: Uri): Flow<ResultsChange<Image>> {
        return realm.query<RealmImage>("pictureUri = $0", uri.toString()).asFlow().flowOn(Dispatchers.IO)
    }

    suspend fun updateVietnameseText(image: RealmImage, newVietnameseText: String) {
        withContext(Dispatchers.IO) {
            realm.write {
                findLatest(image)?.vietnameseText = newVietnameseText
            }
        }
    }

    suspend fun updateEnglishText(image: RealmImage, newEnglishText: String) {
        withContext(Dispatchers.IO) {
            realm.write {
                findLatest(image)?.englishText = newEnglishText
            }
        }

    }

}