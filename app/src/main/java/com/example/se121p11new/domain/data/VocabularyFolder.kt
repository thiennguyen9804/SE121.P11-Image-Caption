package com.example.se121p11new.domain.data

import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

typealias DomainVocabularyFolder = VocabularyFolder

data class VocabularyFolder (
    val id: String = "",
    val vocabularyList: List<Vocabulary> = listOf(),
    val name: String = ""
) {
    fun toRealmVocabularyFolder() = RealmVocabularyFolder().apply {
        _id = ObjectId(this@VocabularyFolder.id)
        name = this@VocabularyFolder.name
        vocabularyList = this@VocabularyFolder.vocabularyList.map {
            it.toRealmVocabulary()
        }.toRealmList()

    }
}