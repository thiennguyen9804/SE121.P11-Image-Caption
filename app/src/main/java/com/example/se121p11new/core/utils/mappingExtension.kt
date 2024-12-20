package com.example.se121p11new.core.utils

import com.example.se121p11new.core.presentation.utils.toIdString
import com.example.se121p11new.data.local.realm_object.RealmImage
import com.example.se121p11new.data.local.realm_object.RealmImageFolder

fun RealmImage.toFirebaseImage(): HashMap<String, String> {
    val res = hashMapOf(
        "_id" to this._id.toIdString(),
        "imageName" to this.imageName,
        "pictureUri" to this.pictureUri,
        "englishText" to this.englishText,
        "vietnameseText" to this.vietnameseText,
        "captureTime" to this.captureTime
    )

    return res
}

fun RealmImageFolder.toFirebaseImageFolder(): HashMap<String, Any> {
    val res = hashMapOf(
        "_id" to this._id.toIdString(),
        "folderName" to this.name,
        "imageList" to this.imageList.map { it._id.toIdString() }
    )

    return res
}