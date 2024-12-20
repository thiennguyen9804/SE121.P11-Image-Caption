package com.example.se121p11new.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteImageFolderDataSource {
    private val fireStoreDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun uploadImageFolderToCloud(userId: String, imageFolder: HashMap<String, Any>) {
        withContext(Dispatchers.IO) {
            fireStoreDb.collection("user")
                .document(userId)
                .collection("imageFolder")
                .document(imageFolder["_id"].toString())
                .set(imageFolder)
        }
    }
}