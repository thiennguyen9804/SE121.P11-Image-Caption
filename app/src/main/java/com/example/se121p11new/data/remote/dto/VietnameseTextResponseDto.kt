package com.example.se121p11new.data.remote.dto


import com.google.gson.annotations.SerializedName

data class VietnameseTextResponseDto(
    @SerializedName("data")
    val `data`: Data
)