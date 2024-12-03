package com.example.se121p11new.data.remote.api

import com.example.se121p11new.data.remote.dto.VocabularyDto
import retrofit2.http.GET
import retrofit2.http.Path

interface VocabularyApi {
    @GET("/words/{word}")
    suspend fun getVocabularyByWord(@Path("word") word: String): VocabularyDto
}