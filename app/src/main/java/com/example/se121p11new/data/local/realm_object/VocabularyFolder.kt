package com.example.se121p11new.data.local.realm_object

import com.example.se121p11new.data.remote.dto.RealmVocabulary
//import com.example.se121p11new.domain.data.DomainVocabularyFolder
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

typealias RealmVocabularyFolder = VocabularyFolder

class VocabularyFolder : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var name: String = "Vocabulary Folder"
    var vocabularyList: RealmSet<Vocabulary> = realmSetOf()
}