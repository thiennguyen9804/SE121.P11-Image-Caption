package com.example.se121p11new.domain.data


import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList

data class Vocabulary(
    val engVocab: String,
    val ipa: String,
    val partOfSpeeches: List<PartOfSpeech>,
    val phrasalVerbs: List<PhrasalVerb>
)