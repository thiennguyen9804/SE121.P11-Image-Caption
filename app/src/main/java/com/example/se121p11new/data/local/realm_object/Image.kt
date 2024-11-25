package com.example.se121p11new.data.local.realm_object

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

class Image : RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var imageName: String = ""
    var pictureUri: String = ""
    var englishText: String = ""
    var vietnameseText: String = ""
}