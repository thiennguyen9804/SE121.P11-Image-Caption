package com.example.se121p11new.data.remote.dto


import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList

typealias RealmVocabulary = com.example.se121p11new.data.local.realm_object.Vocabulary
//typealias DomainVocabulary = com.example.se121p11new.domain.data.Vocabulary

data class VocabularyDto(
    @SerializedName("engVocab")
    val engVocab: String,
    @SerializedName("ipa")
    val ipa: String,
    @SerializedName("partOfSpeeches")
    val partOfSpeeches: List<PartOfSpeechDto>,
    @SerializedName("phrasalVerbs")
    val phrasalVerbs: List<PhrasalVerbDto>
) {
    fun toRealmVocabulary() = RealmVocabulary().apply {
        this.engVocab = this@VocabularyDto.engVocab
        this.ipa = this@VocabularyDto.ipa
        this.partOfSpeeches = this@VocabularyDto.partOfSpeeches.map { it.toRealmPartOfSpeech() }.toRealmList()
        this.phrasalVerbs = this@VocabularyDto.phrasalVerbs.map { it.toRealmPhrasalVerb() }.toRealmList()
    }

//    fun toDomainVocabulary() = DomainVocabulary(
//        idString = "",
//        engVocab = this.engVocab,
//        ipa = this.ipa,
//        partOfSpeeches = this.partOfSpeeches.map { it.toDomainPartOfSpeech() },
//        phrasalVerbs = this.phrasalVerbs.map { it.toDomainPhrasalVerb() },
//        vocabularyList = emptyList()
//    )
}