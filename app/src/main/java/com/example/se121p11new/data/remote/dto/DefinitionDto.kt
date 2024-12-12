package com.example.se121p11new.data.remote.dto


import com.example.se121p11new.data.local.realm_object.Definition
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList

typealias RealmDefinition = Definition
//typealias DomainDefinition = com.example.se121p11new.domain.data.Definition

data class DefinitionDto(
    @SerializedName("definition")
    val definition: String,
    @SerializedName("examples")
    val examples: List<String>
) {
    fun toRealmDefinition() = RealmDefinition().apply {
        this.definition = this@DefinitionDto.definition
        this.examples = this@DefinitionDto.examples.toRealmList()
    }

//    fun toDomainDefinition() = DomainDefinition(
//        definition = this.definition,
//        examples = this.examples
//    )
}