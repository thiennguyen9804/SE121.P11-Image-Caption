package com.example.se121p11new.data.local

import com.example.se121p11new.data.local.dao.VocabularyDao
import com.example.se121p11new.data.local.realm_object.Vocabulary
import javax.inject.Inject

class LocalVocabularyDataSource @Inject constructor(
    private val vocabularyDao: VocabularyDao
) {
    suspend fun addVocabulary(newVocabulary: Vocabulary) =
        vocabularyDao.addVocabulary(newVocabulary)

    fun getAllVocabularies() = vocabularyDao.getAllVocabularies()

    fun getVocabularyByEngVocab(engVocab: String) =
        vocabularyDao.getVocabularyByEngVocab(engVocab)
}