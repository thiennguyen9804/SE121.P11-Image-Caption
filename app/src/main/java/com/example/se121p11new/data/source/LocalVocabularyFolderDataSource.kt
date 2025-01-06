package com.example.se121p11new.data.source

import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import com.example.se121p11new.data.local.realm_object.VocabularyFolder
//import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmVocabulary
//import com.example.se121p11new.domain.data.DomainVocabularyFolder
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class LocalVocabularyFolderDataSource @Inject constructor(
    private val realm: Realm
) {
    fun getAllImageFolder(): Flow<ResultsChange<VocabularyFolder>> {
        return realm.query<VocabularyFolder>().asFlow()
    }

    suspend fun addFolder(newFolder: VocabularyFolder) {
        realm.write {
            newFolder
            copyToRealm(newFolder)
        }.asFlow()
    }

    fun getVocabularyFolderById(id: ObjectId): Flow<ResultsChange<RealmVocabularyFolder>> {
        return realm.query<RealmVocabularyFolder>("_id == $0", id).asFlow()
    }

    suspend fun addVocabularyToFolder(
        vocabulary: RealmVocabulary,
        folder: RealmVocabularyFolder
    ) {
        realm.write {
            findLatest(folder)!!.vocabularyList.add(findLatest(vocabulary)!!)
        }
    }

    suspend fun removeVocabularyOutOfFolder(
        vocabulary: RealmVocabulary,
        folder: RealmVocabularyFolder
    ) {
        realm.write {
            findLatest(folder)!!.vocabularyList.remove(findLatest(vocabulary)!!)
        }
    }
}