package com.example.se121p11new.domain.data


import com.example.se121p11new.data.local.realm_object.VocabularyFolder
import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import com.example.se121p11new.presentation.vocabulary_detail_screen.vocab
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

data class Vocabulary(
    val idString: String,
    val engVocab: String,
    val ipa: String,
    val partOfSpeeches: List<PartOfSpeech>,
    val phrasalVerbs: List<PhrasalVerb>,
    val vocabularyList: List<DomainVocabularyFolder>
) {
    fun toRealmVocabulary() = RealmVocabulary().apply {
        engVocab = this@Vocabulary.engVocab
        ipa = this@Vocabulary.ipa
        partOfSpeeches = this@Vocabulary.partOfSpeeches.map {
            it.toRealmPartOfSpeech()
        }.toRealmList()
        phrasalVerbs = this@Vocabulary.phrasalVerbs.map {
            it.toRealmPhrasalVerb()
        }.toRealmList()
    }
}