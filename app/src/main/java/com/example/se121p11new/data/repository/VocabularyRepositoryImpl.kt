package com.example.se121p11new.data.repository

import android.util.Log
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.LocalVocabularyDataSource
import com.example.se121p11new.data.local.realm_object.Vocabulary
import com.example.se121p11new.data.remote.RemoteImageDataSource
import com.example.se121p11new.data.remote.RemoteVocabularyDataSource
//import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmDefinition
import com.example.se121p11new.data.remote.dto.RealmPartOfSpeech
import com.example.se121p11new.data.remote.dto.RealmVocabulary
//import com.example.se121p11new.domain.data.Definition
//import com.example.se121p11new.domain.data.DomainVocabularyFolder
//import com.example.se121p11new.domain.data.PartOfSpeech
import com.example.se121p11new.domain.repository.VocabularyRepository
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
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
    private val TAG = "VocabularyRepositoryImpl"
    override suspend fun addVocabulary(newVocabulary: Vocabulary) {
        localVocabularyDataSource.addVocabulary(newVocabulary)
    }

    override suspend fun getAllVocabularies(): Flow<List<RealmVocabulary>> =
        localVocabularyDataSource.getAllVocabularies().map { value ->
            value.list.toList()
        }.flowOn(Dispatchers.IO)

    @Deprecated("to be removed before long")
    override suspend fun getAllVocabulariesAndReturnRealmObject(): Flow<ResultsChange<Vocabulary>> {
        return localVocabularyDataSource.getAllVocabularies()
    }

    override suspend fun getVocabularyByEngVocabLocally(
        engVocab: String
    ): Flow<RealmVocabulary?> = channelFlow {
        localVocabularyDataSource.getVocabularyByEngVocabFromCache(engVocab).map { value ->
            val res = value.list.toList().firstOrNull()
            if(res != null) {
                send(res)
            } else {
                localVocabularyDataSource.getVocabularyByEngVocabLocally(engVocab).map { value2 ->
                    val res2 = value2.list.toList().firstOrNull()
                    send(res2)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteVocabularyLocally(vocabulary: Vocabulary) {
        localVocabularyDataSource.deleteVocabularyLocally(vocabulary)
    }


    override suspend fun getVocabularyByEngVocab(
        engVocab: String,
    ): Flow<RealmVocabulary> = channelFlow {
        try {
            getVocabularyByEngVocabLocally(engVocab).collect {
                if(it != null) {
                    send(it)
                } else {
                    return@collect
                }
            }
            remoteVocabularyDataSource.getVocabularyByEngVocab(engVocab).map {
                it.toRealmVocabulary()
            }.collectLatest {
                Log.d(TAG, "found vocab from remote api")
                send(it)
            }
        } catch(e: HttpException) {
            when(e.code()) {
                404 -> {
                    Log.d(TAG, "cannot found vocab from remote api, calling trans api")
                    val respond = async { remoteImageDataSource.generateVietnameseText(engVocab) }.await()
                    val definition = RealmDefinition().apply {
                        definition = respond
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
//                    withContext(Dispatchers.IO) {
//
//                    }
//                    val res = DomainVocabulary(
//                        idString = "",
//                        engVocab = engVocab,
//                        ipa = "",
//                        partOfSpeeches = listOf(
//                            PartOfSpeech(
//                                partOfSpeech = "từ vựng",
//                                definitions = listOf(
//                                    Definition(
//                                        definition = respond,
//                                        examples = emptyList()
//                                    )
//                                )
//                            )
//                        ),
//
//                        phrasalVerbs = emptyList(),
//                        vocabularyList = emptyList()
//
//                    )
                    send(cacheValue)




//                    remoteImageDataSource.generateVietnameseText(engVocab).collectLatest {
//                        when(it) {
//                            is Resource.Error -> send(Resource.Error(message = it.message!!))
//                            is Resource.Loading -> send(Resource.Loading())
//                            is Resource.Success -> {
//                                withContext(Dispatchers.IO) {
//                                    val definition = RealmDefinition().apply {
//                                        definition = it.data!!
//                                        examples = realmListOf()
//                                    }
//                                    val partOfSpeech = RealmPartOfSpeech().apply {
//                                        partOfSpeech = "từ vựng"
//                                        definitions = realmListOf(definition)
//                                    }
//                                    val cacheValue = RealmVocabulary().apply {
//                                        this@apply.engVocab = engVocab
//                                        ipa = ""
//                                        partOfSpeeches = realmListOf(partOfSpeech)
//                                        phrasalVerbs = realmListOf()
//
//                                    }
//                                    localVocabularyDataSource.addVocabularyToCache(cacheValue)
//                                }
//
//                                val res = DomainVocabulary(
//                                    idString = "",
//                                    engVocab = engVocab,
//                                    ipa = "",
//                                    partOfSpeeches = listOf(
//                                        PartOfSpeech(
//                                            partOfSpeech = "từ vựng",
//                                            definitions = listOf(
//                                                Definition(
//                                                    definition = it.data!!,
//                                                    examples = emptyList()
//                                                )
//                                            )
//                                        )
//                                    ),
//
//                                    phrasalVerbs = emptyList(),
//                                    vocabularyList = emptyList()
//
//                                )
//                                send(Resource.Success(res))
//                            }
//                        }
//                    }
                }
                else -> {
                    throw e
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

    override suspend fun updateVocabulary(engVocab: String, newVocabulary: Vocabulary) {
        localVocabularyDataSource.updateVocabulary(engVocab, newVocabulary)
    }


}