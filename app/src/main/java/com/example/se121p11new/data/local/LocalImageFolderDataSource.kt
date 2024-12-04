package com.example.se121p11new.data.local

import com.example.se121p11new.data.local.realm_object.ImageFolder
import com.example.se121p11new.data.local.realm_object.RealmImageFolder
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalImageFolderDataSource @Inject constructor(
    private val realm: Realm,
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
}