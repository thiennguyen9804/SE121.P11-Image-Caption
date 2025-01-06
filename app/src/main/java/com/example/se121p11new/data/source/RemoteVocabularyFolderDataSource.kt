package com.example.se121p11new.data.source

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteVocabularyFolderDataSource {
    private val fireStoreDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun uploadVocabularyFolderToCloud(userId: String, vocabularyFolder: HashMap<String, Any>) {
        withContext(Dispatchers.IO) {
            fireStoreDb.collection("user")
                .document(userId)
                .collection("vocabularyFolder")
                .document(vocabularyFolder["_id"].toString())
                .set(vocabularyFolder)
        }
    }
}