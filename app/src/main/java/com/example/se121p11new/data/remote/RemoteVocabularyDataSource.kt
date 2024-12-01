package com.example.se121p11new.data.remote

import com.example.se121p11new.data.remote.api.VocabularyApi
import javax.inject.Inject

class RemoteVocabularyDataSource @Inject constructor (
    val api: VocabularyApi
) {

}