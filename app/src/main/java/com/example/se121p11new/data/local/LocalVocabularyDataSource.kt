package com.example.se121p11new.data.local

import com.example.se121p11new.data.local.realm_object.Vocabulary
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
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
    suspend fun addVocabularyToCache(newVocabulary: Vocabulary) =
        cache.write {
            newVocabulary
            copyToRealm(newVocabulary)
        }.asFlow()

    fun getVocabularyByEngVocabFromCache(engVocab: String) =
        cache.query<Vocabulary>("engVocab == $0", engVocab).asFlow()

    suspend fun addVocabulary(newVocabulary: Vocabulary) =
        realm.write {
            newVocabulary
            copyToRealm(newVocabulary)
        }.asFlow()

    fun getAllVocabularies() = realm.query<Vocabulary>().asFlow()

    fun getVocabularyByEngVocab(engVocab: String) =
        realm.query<Vocabulary>("engVocab == $0", engVocab).asFlow()
}