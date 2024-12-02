package com.example.se121p11new.data.repository

import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.LocalVocabularyDataSource
import com.example.se121p11new.data.local.realm_object.Vocabulary
import com.example.se121p11new.data.remote.RemoteImageDataSource
import com.example.se121p11new.data.remote.RemoteVocabularyDataSource
import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.domain.data.Definition
import com.example.se121p11new.domain.data.PartOfSpeech
import com.example.se121p11new.domain.repository.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class VocabularyRepositoryImpl @Inject constructor(
    private val remoteVocabularyDataSource: RemoteVocabularyDataSource,
    private val localVocabularyDataSource: LocalVocabularyDataSource,
    private val remoteImageDataSource: RemoteImageDataSource
) : VocabularyRepository {
    override suspend fun addVocabulary(newVocabulary: Vocabulary) {
        localVocabularyDataSource.addVocabulary(newVocabulary)
    }

    override suspend fun getAllVocabularies(): Flow<List<DomainVocabulary>> =
        localVocabularyDataSource.getAllVocabularies().map { value ->
            value.list.map { it.toDomainVocabulary() }
        }.flowOn(Dispatchers.IO)


    override suspend fun getVocabularyByEngVocab(engVocab: String): Flow<Resource<DomainVocabulary>> = flow {
        try {
            remoteVocabularyDataSource.getVocabularyByEngVocab(engVocab).let { value ->
                value.collectLatest {
                    when(it) {
                        is Resource.Error -> emit(Resource.Error(message = it.message!!))
                        is Resource.Loading -> emit(Resource.Loading())
                        is Resource.Success -> emit(Resource.Success(it.data!!.toDomainVocabulary()))
                    }
                }
            }
        } catch(e: HttpException) {
            when(e.code()) {
                404 -> {
                    remoteImageDataSource.generateVietnameseText(engVocab).collectLatest {
                        when(it) {
                            is Resource.Error -> emit(Resource.Error(message = it.message!!))
                            is Resource.Loading -> emit(Resource.Loading())
                            is Resource.Success -> {
                                val res = DomainVocabulary(
                                    engVocab = engVocab,
                                    ipa = "",
                                    partOfSpeeches = listOf(
                                        PartOfSpeech(
                                            partOfSpeech = "từ vựng",
                                            definitions = listOf(
                                                Definition(
                                                    definition = it.data!!,
                                                    examples = emptyList()
                                                )
                                            )
                                        )
                                    ),

                                    phrasalVerbs = emptyList()
                                )
                                emit(Resource.Success(res))
                            }
                        }
                    }
                }
            }
        }
    }
}