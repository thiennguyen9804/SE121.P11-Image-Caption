package com.example.se121p11new.data.local.realm_object

//import com.example.se121p11new.data.remote.dto.DomainDefinition
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList


class Definition : EmbeddedRealmObject {
    var definition: String = ""
    var examples: RealmList<String> = realmListOf()
//    var partOfSpeech: PartOfSpeech? = null
//    var phrasalVerb: PhrasalVerb? = null
//    fun toDomainDefinition() = DomainDefinition(
//        definition = definition,
//        examples = examples
//    )
}