package com.example.se121p11new.domain.data


import com.example.se121p11new.data.remote.dto.RealmPhrasalVerb
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList


data class PhrasalVerb(
    val definitions: List<Definition>,
    val phrasalVerb: String
) {
    fun toRealmPhrasalVerb() = RealmPhrasalVerb().apply {
        phrasalVerb = this@PhrasalVerb.phrasalVerb
        this.definitions = this@PhrasalVerb.definitions.map {
            it.toRealmDefinition()
        }.toRealmList()
    }
}