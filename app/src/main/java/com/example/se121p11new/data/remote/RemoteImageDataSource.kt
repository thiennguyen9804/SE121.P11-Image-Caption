package com.example.se121p11new.data.remote

import android.graphics.Bitmap
import android.net.Uri
import com.example.se121p11new.core.data.PromptConstants
import com.example.se121p11new.core.presentation.utils.Resource
import com.facebook.internal.WebDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
//import com.google.cloud.translate.Translate
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor(
    private val generativeModel: GenerativeModel,
//    private val translate: Translate
) {
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
//        return withContext(Dispatchers.IO) {
//            val translated = translate.translate(
//                englishText,
//                Translate.TranslateOption.targetLanguage("vi"),
//                Translate.TranslateOption.model("base")
//            )
//            return@withContext translated.translatedText!!
//        }
        delay(500)
        return "Kiểm thử"
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