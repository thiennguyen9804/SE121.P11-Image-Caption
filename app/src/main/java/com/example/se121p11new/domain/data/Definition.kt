package com.example.se121p11new.domain.data


import com.example.se121p11new.data.local.realm_object.Definition
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.toRealmList

data class Definition(
    val definition: String,
    val examples: List<String>
)