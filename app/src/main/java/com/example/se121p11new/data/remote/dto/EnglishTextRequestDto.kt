package com.example.se121p11new.data.remote.dto


import com.google.gson.annotations.SerializedName

data class EnglishTextRequestDto(
    @SerializedName("q")
    val q: List<String>,
    @SerializedName("source")
    val source: String,
    @SerializedName("target")
    val target: String
)