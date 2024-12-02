package com.example.se121p11new.data.remote.dto


import com.example.se121p11new.domain.data.PartOfSpeech
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList

typealias RealmPartOfSpeech = com.example.se121p11new.data.local.realm_object.PartOfSpeech
typealias DomainPartOfSpeech = PartOfSpeech

data class PartOfSpeechDto(
    @SerializedName("definitions")
    val definitions: List<DefinitionDto>,
    @SerializedName("partOfSpeech")
    val partOfSpeech: String
) {
    fun toRealmPartOfSpeech() = RealmPartOfSpeech().apply {
        this.partOfSpeech = this@PartOfSpeechDto.partOfSpeech
        this.definitions = this@PartOfSpeechDto.definitions.map { it.toRealmDefinition() }.toRealmList()

    }

    fun toDomainPartOfSpeech() = DomainPartOfSpeech(
        partOfSpeech = this.partOfSpeech,
        definitions = this.definitions.map { it.toDomainDefinition() }
    )
}