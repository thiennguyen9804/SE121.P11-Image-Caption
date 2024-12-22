package com.example.se121p11new.core.utils

import com.example.se121p11new.core.presentation.utils.toIdString
import com.example.se121p11new.data.local.realm_object.RealmImage
import com.example.se121p11new.data.local.realm_object.RealmImageFolder
import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import com.example.se121p11new.data.remote.dto.RealmDefinition
import com.example.se121p11new.data.remote.dto.RealmPartOfSpeech
import com.example.se121p11new.data.remote.dto.RealmPhrasalVerb
import com.example.se121p11new.data.remote.dto.RealmVocabulary

fun RealmVocabularyFolder.toMap(): HashMap<String, Any> {
    val res = hashMapOf(
        "_id" to this._id.toIdString(),
        "folderName" to this.name,
        "vocabularyList" to this.vocabularyList.map { it._id.toIdString() }
    )

    return res
}

fun RealmImage.toMap(): HashMap<String, String> {
    val res = hashMapOf(
        "_id" to this._id.toIdString(),
        "imageName" to this.imageName,
        "pictureUri" to this.pictureUri,
        "englishText" to this.englishText,
        "vietnameseText" to this.vietnameseText,
        "captureTime" to this.captureTime
    )

    return res
}

fun RealmImageFolder.toMap(): HashMap<String, Any> {
    val res = hashMapOf(
        "_id" to this._id.toIdString(),
        "folderName" to this.name,
        "imageList" to this.imageList.map { it._id.toIdString() }
    )

    return res
}

fun RealmVocabulary.toMap(): HashMap<String, Any> {
    val res = hashMapOf(
        "_id" to this._id.toIdString(),
        "engVocab" to this.engVocab,
        "ipa" to this.ipa,
        "phrasalVerbs" to this.phrasalVerbs.map(RealmPhrasalVerb::toMap),
        "partOfSpeeches" to this.partOfSpeeches.map(RealmPartOfSpeech::toMap)
    )

    return res
}

fun RealmPhrasalVerb.toMap(): HashMap<String, Any> {
    val res = hashMapOf(
        "phrasalVerb" to this.phrasalVerb,
        "definitions" to this.definitions.map(RealmDefinition::toMap)
    )

    return res
}

fun RealmPartOfSpeech.toMap(): HashMap<String, Any> {
    val res = hashMapOf(
        "partOfSpeech" to this.partOfSpeech,
        "definitions" to this.definitions.map(RealmDefinition::toMap)
    )

    return res
}

fun RealmDefinition.toMap(): HashMap<String, Any> {
    val res = hashMapOf(
        "definition" to this.definition,
        "examples" to this.examples
    )

    return res
}