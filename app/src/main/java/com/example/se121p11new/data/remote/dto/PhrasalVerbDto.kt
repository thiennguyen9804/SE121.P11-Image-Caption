package com.example.se121p11new.data.remote.dto


import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList

typealias RealmPhrasalVerb = com.example.se121p11new.data.local.realm_object.PhrasalVerb

data class PhrasalVerbDto(
    @SerializedName("definitions")
    val definitions: List<DefinitionDto>,
    @SerializedName("phrasalVerb")
    val phrasalVerb: String
) {
    fun toRealmPhrasalVerb() = RealmPhrasalVerb().apply {
        this.phrasalVerb = this@PhrasalVerbDto.phrasalVerb
        this.definitions = this@PhrasalVerbDto.definitions.map { it.toRealmDefinition() }.toRealmList()
    }
}