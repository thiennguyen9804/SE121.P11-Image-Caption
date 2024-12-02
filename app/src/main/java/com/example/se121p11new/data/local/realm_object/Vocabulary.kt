package com.example.se121p11new.data.local.realm_object

import com.example.se121p11new.data.remote.dto.DomainVocabulary
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Vocabulary : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var engVocab: String = ""
    var ipa: String = ""
    var partOfSpeeches: RealmList<PartOfSpeech> = realmListOf()
    var phrasalVerbs: RealmList<PhrasalVerb> = realmListOf()

    fun toDomainVocabulary() = DomainVocabulary(
        engVocab = engVocab,
        ipa = ipa,
        partOfSpeeches = partOfSpeeches.map { it.toDomainPartOfSpeech() },
        phrasalVerbs = phrasalVerbs.map { it.toDomainPhrasalVerb() }
    )
}