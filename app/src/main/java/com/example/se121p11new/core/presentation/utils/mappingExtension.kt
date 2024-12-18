package com.example.se121p11new.core.presentation.utils

import com.example.se121p11new.data.local.realm_object.RealmImage

fun RealmImage.toFirebaseImage(): Map<String, String> {
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