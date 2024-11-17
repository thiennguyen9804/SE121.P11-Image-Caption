package com.example.se121p11new.data.remote

import com.example.se121p11new.core.data.AppConstants
import com.example.se121p11new.data.remote.dto.EnglishTextRequestDto
import com.example.se121p11new.data.remote.dto.VietnameseTextResponseDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GcpApi {
    @POST(".")
    suspend fun translateEnglishToVietnamese(
        @Body request: EnglishTextRequestDto,
    ) : VietnameseTextResponseDto
}