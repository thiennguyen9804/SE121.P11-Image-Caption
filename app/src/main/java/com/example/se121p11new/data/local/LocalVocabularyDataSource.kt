package com.example.se121p11new.data.local

import com.example.se121p11new.data.local.realm_object.Vocabulary
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class LocalVocabularyDataSource @Inject constructor(
    private val realm: Realm,
    private val cache: Realm
) {
    suspend fun clearCache() {
        cache.write {
            deleteAll()
        }
    }
    suspend fun addVocabularyToCache(newVocabulary: Vocabulary) {
        withContext(Dispatchers.IO) {
            cache.write {
                val temp = query<Vocabulary>("engVocab == $0", newVocabulary.engVocab).first().find()
                if (temp != null) {
                    return@write
                }
                copyToRealm(newVocabulary, UpdatePolicy.ALL)
            }
        }
    }

    fun getVocabularyByEngVocabFromCache(engVocab: String) =
        cache.query<Vocabulary>("engVocab == $0", engVocab).asFlow().flowOn(Dispatchers.IO)

    fun getVocabularyByEngVocabLocally(engVocab: String) =
        realm.query<Vocabulary>("engVocab == $0", engVocab).asFlow().flowOn(Dispatchers.IO)

    suspend fun addVocabulary(newVocabulary: Vocabulary) {
        withContext(Dispatchers.IO) {
            realm.write {
                val temp = query<Vocabulary>("engVocab == $0", newVocabulary.engVocab).first().find()
                if(temp != null) {
                    return@write
                }
                copyToRealm(newVocabulary, UpdatePolicy.ALL)
            }
        }
    }

    suspend fun updateVocabulary(engVocab: String, newVocabulary: Vocabulary) {
        withContext(Dispatchers.IO) {
            realm.write {
                val oldVocabulary = query<Vocabulary>("engVocab == $0", engVocab).first().find() ?: return@write
                findLatest(oldVocabulary)!!.ipa = newVocabulary.ipa
                findLatest(oldVocabulary)!!.partOfSpeeches = newVocabulary.partOfSpeeches
                findLatest(oldVocabulary)!!.phrasalVerbs = newVocabulary.phrasalVerbs
            }
        }
    }

    fun getAllVocabularies() = realm.query<Vocabulary>().asFlow()

    suspend fun deleteVocabularyLocally(vocabulary: Vocabulary) {
        realm.write {
            val res = findLatest(vocabulary) ?: return@write
            delete(res)
        }
    }
}