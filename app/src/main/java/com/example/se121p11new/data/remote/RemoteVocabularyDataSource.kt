package com.example.se121p11new.data.remote

import android.util.Log
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.remote.api.VocabularyApi
import com.example.se121p11new.data.remote.dto.VocabularyDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RemoteVocabularyDataSource @Inject constructor (
    private val api: VocabularyApi
) {
    private val TAG = "RemoteVocabularyDataSource"
    private val fireStoreDb: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getVocabularyByEngVocab(engVocab: String) = flow {
        Log.d(TAG, engVocab)
        val req = engVocab.lowercase()
        try {
            val res = api.getVocabularyByWord(req)
            emit(res)
        } catch(e: HttpException) {
            when(e.code()) {
                404 -> {
                    Log.d(TAG, "cannot found vocab from remote api")
                    throw e
                }
                in 500..509 -> {
                    throw Exception("Server hiện không thể đáp ứng, thử lại sau")
                }
                else -> {
                    throw Exception("Gặp lỗi!!! Vui lòng thử lại sau")
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun uploadVocabulary(userId: String, vocabulary: HashMap<String, Any>) {
        withContext(Dispatchers.IO) {
            fireStoreDb.collection("user")
                .document(userId)
                .collection("vocabulary")
                .document(vocabulary["_id"].toString())
                .set(vocabulary)
        }
    }
}