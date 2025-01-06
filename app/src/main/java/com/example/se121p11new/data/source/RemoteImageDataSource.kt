package com.example.se121p11new.data.source

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.se121p11new.core.data.PromptConstants
import com.example.se121p11new.data.remote.api.VocabularyApi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
//import com.google.cloud.translate.Translate
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.type.content
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val api: VocabularyApi,
//    private val translate: Translate
) {
    private val TAG = "RemoteImageDataSource"
    private val fireStoreDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    suspend fun generateEnglishText(bitmap: Bitmap): String {
//        emit(Resource.Loading())
        val prompt = content {
            image(bitmap)
            text(PromptConstants.BASIC_SENTENCE)
        }

        val response = generativeModel.generateContent(prompt)
        return response.text!!
//        delay(500)
//        emit(Resource.Success("Test"))
    }

    suspend fun generateVietnameseText(englishText: String): String {
        try {
//            delay(3000)
//            Log.d(TAG, englishText)
//            return "Kiểm thử"
            val response = api.translate(englishText)
            val result = response.result
            Log.d(TAG, "translate result: $result")
            return result
        } catch(e: HttpException) {
            Log.e(TAG, e.code().toString())
            return ""
        } catch(e: CancellationException) {
            throw e
        } catch(e: Exception) {

            e.printStackTrace()
        }

        return "Đang xây dựng!!!"
//        delay(500)
//        return "Kiểm thử"
    }

    suspend fun uploadFileToCloud(
        userId: String,
        path: String,
        onCompleteListener: com.google.android.gms.tasks.OnCompleteListener<Uri>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val storageRef = storage.reference
                val uri = Uri.parse(path)
                val imagesRef = storageRef.child("image/$userId/${uri.lastPathSegment}")
                val uploadTask = imagesRef.putFile(uri)
                val urlTask =  uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    imagesRef.downloadUrl
                }.addOnCompleteListener(onCompleteListener)
//                val task = async {imagesRef.putFile(uri)}.await()
            } catch (e: Exception) {
                ensureActive()
                e.printStackTrace()
            }
            return@withContext ""
        }
    }

    suspend fun uploadImageToCloud(userId: String, image: HashMap<String, String>) {
        withContext(Dispatchers.IO) {
            fireStoreDb.collection("user")
                .document(userId)
                .collection("image")
                .document(image["_id"].toString())
                .set(image)
        }

    }
}