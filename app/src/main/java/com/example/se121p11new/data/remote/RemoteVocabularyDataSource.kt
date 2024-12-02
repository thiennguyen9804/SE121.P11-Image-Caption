package com.example.se121p11new.data.remote

import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.remote.api.VocabularyApi
import com.example.se121p11new.data.remote.dto.VocabularyDto
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class RemoteVocabularyDataSource @Inject constructor (
    private val api: VocabularyApi
) {
    fun getVocabularyByEngVocab(engVocab: String) = flow<Resource<VocabularyDto>> {
        try {
            api.getVocabularyByWord(engVocab)
        } catch(e: HttpException) {
            when(e.code()) {
                404 -> {
                    throw e
                }
                in 500..509 -> {
                    emit(Resource.Error("Hiện server không thể tiếp nhận yêu cầu, vui lòng thử lại sau"))
                }
                else -> {
                    emit(Resource.Error("Gặp lỗi!!! Vui lòng thử lại sau"))
                }
            }
        }
    }
}