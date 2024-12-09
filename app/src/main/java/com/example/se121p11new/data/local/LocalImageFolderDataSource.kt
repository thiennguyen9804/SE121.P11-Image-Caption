package com.example.se121p11new.data.local

import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.ImageFolder
import com.example.se121p11new.data.local.realm_object.RealmImageFolder
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mongodb.kbson.ObjectId
import javax.inject.Inject
import javax.inject.Named

class LocalImageFolderDataSource @Inject constructor(
    @Named("appDb") private val realm: Realm,
) {
    suspend fun createFolder(folder: RealmImageFolder) {
        realm.write {
            folder
            copyToRealm(folder)
        }.asFlow()
    }

    fun getAllImageFolder(): Flow<ResultsChange<ImageFolder>> {
        return realm.query<ImageFolder>().asFlow()
    }

    fun getImageFolderById(id: ObjectId): Flow<ResultsChange<ImageFolder>> {
        return realm.query<RealmImageFolder>("_id == $0", id).asFlow()
    }

    suspend fun addImageToFolder(image: Image, folder: ImageFolder) {
        realm.write {
            findLatest(folder)!!.imageList.add(findLatest(image)!!)
        }
    }

    suspend fun removeImageOutOfFolder(image: Image, folder: ImageFolder) {
        realm.write {
            findLatest(folder)!!.imageList.remove(findLatest(image)!!)
        }
    }

    suspend fun deleteFolder(folder: ImageFolder) {
        realm.write {
            val toBeDeleted = findLatest(folder) ?: return@write
            delete(toBeDeleted)
        }
    }
}