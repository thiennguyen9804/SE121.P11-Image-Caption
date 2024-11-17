package com.example.se121p11new.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("translations")
    val translations: List<Translation>
)