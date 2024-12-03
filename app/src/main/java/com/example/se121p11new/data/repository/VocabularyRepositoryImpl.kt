package com.example.se121p11new.data.repository

import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.LocalVocabularyDataSource
import com.example.se121p11new.data.local.realm_object.Vocabulary
import com.example.se121p11new.data.remote.RemoteImageDataSource
import com.example.se121p11new.data.remote.RemoteVocabularyDataSource
import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmDefinition
import com.example.se121p11new.data.remote.dto.RealmPartOfSpeech
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import com.example.se121p11new.domain.data.Definition
import com.example.se121p11new.domain.data.PartOfSpeech
import com.example.se121p11new.domain.repository.VocabularyRepository
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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


    override suspend fun getVocabularyByEngVocab(
        engVocab: String,
    ): Flow<Resource<DomainVocabulary>> = channelFlow {
        try {
            localVocabularyDataSource.getVocabularyByEngVocabFromCache(engVocab).map { value ->
                println("vocab repo: searching for cache value...")
                val res = value.list.toList().firstOrNull()
                if(res == null) {
                    println("vocab repo: cache misses")
                    return@map
                }
                println("vocab repo: cache hits")
                send(Resource.Success(res.toDomainVocabulary()))
            }
            remoteVocabularyDataSource.getVocabularyByEngVocab(engVocab).let { value ->
                value.collectLatest {
                    when(it) {
                        is Resource.Error -> send(Resource.Error(message = it.message!!))
                        is Resource.Loading -> send(Resource.Loading())
                        is Resource.Success -> {
                            println("vocab repo: backend hits")
                            localVocabularyDataSource.addVocabularyToCache(it.data!!.toRealmVocabulary())
                            send(Resource.Success(it.data.toDomainVocabulary()))
                        }
                    }
                }
            }
        } catch(e: HttpException) {
            when(e.code()) {
                404 -> {
                    println("vocab repo: backend misses, looking for data in gcp...")
                    remoteImageDataSource.generateVietnameseText(engVocab).collectLatest {
                        when(it) {
                            is Resource.Error -> send(Resource.Error(message = it.message!!))
                            is Resource.Loading -> send(Resource.Loading())
                            is Resource.Success -> {
                                withContext(Dispatchers.IO) {
                                    val definition = RealmDefinition().apply {
                                        definition = it.data!!
                                        examples = realmListOf()
                                    }
                                    val partOfSpeech = RealmPartOfSpeech().apply {
                                        partOfSpeech = "từ vựng"
                                        definitions = realmListOf(definition)
                                    }
                                    val cacheValue = RealmVocabulary().apply {
                                        this@apply.engVocab = engVocab
                                        ipa = ""
                                        partOfSpeeches = realmListOf(partOfSpeech)
                                        phrasalVerbs = realmListOf()

                                    }
                                    localVocabularyDataSource.addVocabularyToCache(cacheValue)
                                }

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
                                send(Resource.Success(res))
                            }
                        }
                    }
                }
                else -> {
                    send(Resource.Error(e.message!!))
                }
            }
        } catch(e: CancellationException) {
            throw e
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun clearCache() {
        localVocabularyDataSource.clearCache()
    }
}