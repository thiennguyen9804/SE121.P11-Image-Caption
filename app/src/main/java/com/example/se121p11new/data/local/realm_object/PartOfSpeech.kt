package com.example.se121p11new.data.local.realm_object

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class PartOfSpeech : RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var partOfSpeech: String = ""
    var definitions: RealmList<Definition> = realmListOf()

}