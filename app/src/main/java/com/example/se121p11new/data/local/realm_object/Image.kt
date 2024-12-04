package com.example.se121p11new.data.local.realm_object

import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

typealias RealmImage = Image

class Image : RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var imageName: String = ""
    var pictureUri: String = ""
    var englishText: String = ""
    var vietnameseText: String = ""
    var captureTime: String = ""
    val imageList : RealmResults<ImageFolder> by backlinks(ImageFolder::imageList)
}