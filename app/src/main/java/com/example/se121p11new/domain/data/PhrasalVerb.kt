package com.example.se121p11new.domain.data


import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList


data class PhrasalVerb(
    val definitions: List<Definition>,
    val phrasalVerb: String
)