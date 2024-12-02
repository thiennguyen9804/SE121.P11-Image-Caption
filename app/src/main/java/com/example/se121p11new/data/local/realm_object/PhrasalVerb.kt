package com.example.se121p11new.data.local.realm_object

import com.example.se121p11new.data.remote.dto.DomainPhrasalVerb
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList

class PhrasalVerb : EmbeddedRealmObject {
    var phrasalVerb: String = ""
    var definitions: RealmList<Definition> = realmListOf()
    var vocabulary: Vocabulary? = null

    fun toDomainPhrasalVerb() = DomainPhrasalVerb(
        phrasalVerb = phrasalVerb,
        definitions = definitions.map { it.toDomainDefinition() }
    )
}