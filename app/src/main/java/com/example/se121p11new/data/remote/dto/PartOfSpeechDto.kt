package com.example.se121p11new.data.remote.dto


import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList

typealias RealmPartOfSpeech = com.example.se121p11new.data.local.realm_object.PartOfSpeech

data class PartOfSpeechDto(
    @SerializedName("definitions")
    val definitions: List<DefinitionDto>,
    @SerializedName("partOfSpeech")
    val partOfSpeech: String
) {
    fun toReamPartOfSpeech() = RealmPartOfSpeech().apply {
        this.partOfSpeech = this@PartOfSpeechDto.partOfSpeech
        this.definitions = this@PartOfSpeechDto.definitions.map { it.toRealmDefinition() }.toRealmList()

    }
}