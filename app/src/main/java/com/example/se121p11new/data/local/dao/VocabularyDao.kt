package com.example.se121p11new.data.local.dao

import com.example.se121p11new.data.local.realm_object.Vocabulary
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import javax.inject.Inject

class VocabularyDao @Inject constructor(
    private val realm: Realm
) {
    suspend fun addVocabulary(newVocabulary: Vocabulary) = realm.write {
        copyToRealm(newVocabulary)
    }.asFlow()

    fun getAllVocabularies() = realm.query<Vocabulary>().asFlow()

    fun getVocabularyByEngVocab(engVocab: String) = realm.query<Vocabulary>("engVocab == $0", engVocab).asFlow()
}