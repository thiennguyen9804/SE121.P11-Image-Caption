package com.example.se121p11new.data.remote.dto


import com.google.gson.annotations.SerializedName

data class EnglishTextRequestDto(
    @SerializedName("format")
    val format: String,
    @SerializedName("q")
    val q: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("target")
    val target: String
)