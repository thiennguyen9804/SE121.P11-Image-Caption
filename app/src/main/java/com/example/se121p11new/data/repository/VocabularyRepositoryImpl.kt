package com.example.se121p11new.data.repository

import android.util.Log
import com.example.se121p11new.data.source.LocalVocabularyDataSource
import com.example.se121p11new.data.local.realm_object.Vocabulary
import com.example.se121p11new.data.source.RemoteImageDataSource
import com.example.se121p11new.data.source.RemoteVocabularyDataSource
//import com.example.se121p11new.data.remote.dto.DomainVocabulary
import com.example.se121p11new.data.remote.dto.RealmDefinition
import com.example.se121p11new.data.remote.dto.RealmPartOfSpeech
import com.example.se121p11new.data.remote.dto.RealmVocabulary
//import com.example.se121p11new.domain.data.Definition
//import com.example.se121p11new.domain.data.DomainVocabularyFolder
//import com.example.se121p11new.domain.data.PartOfSpeech
import com.example.se121p11new.domain.repository.VocabularyRepository
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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

    override fun getFirstNSavedVocabularies(n: Int): Flow<List<RealmVocabulary>> {
        return localVocabularyDataSource.getFirstNSavedVocabularies(n).map {
            it.list.toList()
        }
    }

    @Deprecated("to be removed before long")
    override suspend fun getAllVocabulariesAndReturnRealmObject(): Flow<ResultsChange<Vocabulary>> {
        return localVocabularyDataSource.getAllVocabularies()
    }

    override suspend fun getVocabularyByEngVocabLocally(
        engVocab: String
    ): Flow<RealmVocabulary?> = channelFlow {
//        localVocabularyDataSource.getVocabularyByEngVocabFromCache(engVocab).collectLatest { value ->
//            val res = value.list.toList().firstOrNull()
//            Log.d(TAG, "found vocab from local cache: ${res?.engVocab}")
//            if(res != null) {
//                send(res)
//            } else {
//                localVocabularyDataSource.getVocabularyByEngVocabLocally(engVocab).collectLatest { value2 ->
//                    val res2 = value2.list.toList().firstOrNull()
//                    Log.d(TAG, "found vocab from local db: ${res2?.engVocab}")
//                    send(res2)
//                }
//            }
//        }

        val cachedResult = localVocabularyDataSource.getVocabularyByEngVocabFromCache(engVocab).firstOrNull()?.list?.firstOrNull()
        if (cachedResult != null) {
            send(cachedResult) // Gửi giá trị từ cache
            return@channelFlow
        }

        // Lấy từ database nếu cache không có
        val dbResult = localVocabularyDataSource.getVocabularyByEngVocabLocally(engVocab).firstOrNull()?.list?.firstOrNull()
        send(dbResult) // Gửi giá trị từ database
    }

    private fun mapVocabTranslateToRealm(engVocab: String): RealmVocabulary {
        val definition = RealmDefinition().apply {
            definition = engVocab
            examples = realmListOf()
        }
        val partOfSpeech = RealmPartOfSpeech().apply {
            partOfSpeech = "từ vựng"
            definitions = realmListOf(definition)
        }
        val realmValue = RealmVocabulary().apply {
            this@apply.engVocab = engVocab
            ipa = ""
            partOfSpeeches = realmListOf(partOfSpeech)
            phrasalVerbs = realmListOf()
        }

        return realmValue
    }

    override suspend fun getVocabularyByEngVocabRemotely(engVocab: String): Flow<RealmVocabulary> = channelFlow {
        try {
            remoteVocabularyDataSource.getVocabularyByEngVocab(engVocab).map { vocab ->
                vocab.toRealmVocabulary()
            }.collectLatest { vocab ->
                send(vocab)
            }
        } catch(e: HttpException) {
            when(e.code()) {
                404 -> {
                    Log.d(TAG, "cannot found vocab from remote api, calling trans api")
                    val respond = async { remoteImageDataSource.generateVietnameseText(engVocab) }.await()
                    val cacheValue = mapVocabTranslateToRealm(respond)
                    localVocabularyDataSource.addVocabularyToCache(cacheValue)
                    send(cacheValue)
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

    override suspend fun deleteVocabularyLocally(vocabulary: Vocabulary) {
        localVocabularyDataSource.deleteVocabularyLocally(vocabulary)
    }

    override suspend fun getVocabularyByEngVocab(
        engVocab: String,
    ): Flow<RealmVocabulary> = channelFlow  {
        getVocabularyByEngVocabLocally(engVocab).distinctUntilChanged().collectLatest {
            if(it != null) {
                send(it)
            } else {
                getVocabularyByEngVocabRemotely(engVocab).collectLatest { vocab ->
                    send(vocab)
                }
            }
        }
    }

    override suspend fun clearCache() {
        localVocabularyDataSource.clearCache()
    }

    override suspend fun updateVocabulary(engVocab: String, newVocabulary: Vocabulary) {
        localVocabularyDataSource.updateVocabulary(engVocab, newVocabulary)
    }

    override suspend fun uploadVocabulary(userId: String, vocabulary: HashMap<String, Any>) {
        remoteVocabularyDataSource.uploadVocabulary(userId, vocabulary)
    }


}