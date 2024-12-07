package com.example.se121p11new.domain.data


import com.example.se121p11new.data.remote.dto.RealmPartOfSpeech
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList


data class PartOfSpeech(
    val definitions: List<Definition>,
    val partOfSpeech: String
) {
    fun toRealmPartOfSpeech() = RealmPartOfSpeech().apply {
        partOfSpeech = this@PartOfSpeech.partOfSpeech
        this.definitions = this@PartOfSpeech.definitions.map {
            it.toRealmDefinition()
        }.toRealmList()
    }
}