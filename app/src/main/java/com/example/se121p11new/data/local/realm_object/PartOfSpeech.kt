package com.example.se121p11new.data.local.realm_object

//import com.example.se121p11new.data.remote.dto.DomainPartOfSpeech
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList

class PartOfSpeech : EmbeddedRealmObject {
    var partOfSpeech: String = ""
    var definitions: RealmList<Definition> = realmListOf()
    var vocabulary: Vocabulary? = null

//    fun toDomainPartOfSpeech() = DomainPartOfSpeech(
//        partOfSpeech = partOfSpeech,
//        definitions = definitions.map { it.toDomainDefinition() }
//
//    )
}