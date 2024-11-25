package com.example.se121p11new.data.remote

import android.graphics.Bitmap
import com.example.se121p11new.core.data.PromptConstants
import com.example.se121p11new.core.presentation.utils.Resource
import com.google.cloud.translate.Translate
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val translate: Translate
) {
    fun generateEnglishText(bitmap: Bitmap) = flow {
        emit(Resource.Loading())
        val prompt = content {
            image(bitmap)
            text(PromptConstants.BASIC_SENTENCE)
        }

        val response = generativeModel.generateContent(prompt)
        response.text?.let {
            emit(Resource.Success(it))
        }
//        delay(500)
//        emit(Resource.Success("Test"))
    }.flowOn(Dispatchers.IO)

    fun generateVietnameseText(englishText: String) = flow {
        emit(Resource.Loading())
        val translated = translate.translate(
            englishText,
            Translate.TranslateOption.targetLanguage("vi"),
            Translate.TranslateOption.model("base")
        )
        translated.translatedText?.let {
            emit(Resource.Success(it))
        }
//        delay(500)
//        emit(Resource.Success("Kiểm thử"))
    }.flowOn(Dispatchers.IO)
}