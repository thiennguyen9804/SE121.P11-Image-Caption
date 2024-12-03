package com.example.se121p11new.data.local

import com.example.se121p11new.data.remote.dto.RealmDefinition
import com.example.se121p11new.data.remote.dto.RealmPartOfSpeech
import com.example.se121p11new.data.remote.dto.RealmPhrasalVerb
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import org.junit.Before

class MockLocalVocabularyDataSource {
    private lateinit var cache: Realm
    private val definition1 = RealmDefinition().apply {
        definition = "nghe thất, được nghe, được biết"
        examples = realmListOf(
            "to learn a piece of news from someone: biết tin qua ai"
        )
    }

    private val definition2 = RealmDefinition().apply {
        definition = "học, học tập, nghiên cứu"
        examples = realmListOf()
    }

    private val definition3 = RealmDefinition().apply {
        definition = "tôi chưa biết như thế nào, để còn xem đã"
        examples = realmListOf()
    }

    private val partOfSpeech1 = RealmPartOfSpeech().apply {
        partOfSpeech = "ngoại động từ learnt  /lə:nt/"
        definitions = realmListOf(definition1, definition2)
    }

    private val phrasalVerb1 = RealmPhrasalVerb().apply {
        phrasalVerb = "I am (have) yet to learn"
        definitions = realmListOf(definition3)
    }

    val vocab = RealmVocabulary().apply {
        engVocab = "learn"
        ipa = "/lə:n/"
        partOfSpeeches = realmListOf(partOfSpeech1)
        phrasalVerbs = realmListOf(phrasalVerb1)
    }


    @Before
    fun setUp() {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                RealmVocabulary::class,
                RealmPhrasalVerb::class,
                RealmDefinition::class,
                RealmPartOfSpeech::class
            )
        )
            .inMemory()
            .initialData {
                vocab
            }
            .name("cache.realm")
            .build()

        cache = Realm.open(configuration = config)

    }

    fun getVocabularyByEngVocabFromCache() {
        cache.query<RealmVocabulary>("engVocab == $0", "learn").asFlow()
    }
}