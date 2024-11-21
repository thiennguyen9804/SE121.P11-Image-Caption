package com.example.se121p11new.data.remote

import android.graphics.Bitmap
import com.example.se121p11new.core.data.PromptConstants
import com.example.se121p11new.core.presentation.utils.Resource
import com.google.cloud.translate.Translate
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.type.content
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val translate: Translate
) {
    suspend fun generateEnglishText(bitmap: Bitmap): Resource<String> {
        val prompt = content {
            image(bitmap)
            text(PromptConstants.BASIC_SENTENCE)
        }

        val response = generativeModel.generateContent(prompt)
        val result = response.text
        result?.let {
            return Resource.Success(it)
        }

        return Resource.Error("Something went wrong!!!")
    }

    fun generateVietnameseText(englishText: String): Resource<String> {
        try {
            val translated = translate.translate(
                englishText,
                Translate.TranslateOption.targetLanguage("vi"),
                Translate.TranslateOption.model("base")
            )
            return Resource.Success(translated.translatedText)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Something went wrong!!!")
        }

    }

}