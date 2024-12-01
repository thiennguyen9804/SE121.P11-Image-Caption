package com.example.se121p11new.data.repository

import com.example.se121p11new.data.local.LocalVocabularyDataSource
import com.example.se121p11new.data.remote.RemoteVocabularyDataSource
import javax.inject.Inject

class VocabularyRepositoryImpl @Inject constructor(
    private val remoteVocabularyDataSource: RemoteVocabularyDataSource,
    private val localVocabularyDataSource: LocalVocabularyDataSource
) {

}