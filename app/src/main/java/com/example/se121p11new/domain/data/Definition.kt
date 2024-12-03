package com.example.se121p11new.domain.data


import com.example.se121p11new.data.local.realm_object.Definition
import com.example.se121p11new.data.remote.dto.RealmDefinition
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList

data class Definition(
    val definition: String,
    val examples: List<String>
) {
    fun toRealmDefinition() = RealmDefinition().apply {
        definition = this@Definition.definition
//        val realmExamples: RealmList<String> = examples.map {
//            it
//        }.toRealmList()
        examples = examples.toRealmList()
    }
}