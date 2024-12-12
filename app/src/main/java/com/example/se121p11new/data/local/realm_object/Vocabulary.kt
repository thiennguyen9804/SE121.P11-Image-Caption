package com.example.se121p11new.data.local.realm_object

//import com.example.se121p11new.data.remote.dto.DomainVocabulary
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Vocabulary : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var engVocab: String = ""
    var ipa: String = ""
    var partOfSpeeches: RealmList<PartOfSpeech> = realmListOf()
    var phrasalVerbs: RealmList<PhrasalVerb> = realmListOf()
    val vocabularyList : RealmResults<VocabularyFolder> by backlinks(VocabularyFolder::vocabularyList)

//    fun toDomainVocabulary() = DomainVocabulary(
//        idString = _id.toString().substringAfter("(").substringBefore(")"),
//        engVocab = engVocab,
//        ipa = ipa,
//        partOfSpeeches = partOfSpeeches.map { it.toDomainPartOfSpeech() },
//        phrasalVerbs = phrasalVerbs.map { it.toDomainPhrasalVerb() },
//        vocabularyList = this@Vocabulary.vocabularyList.map {
//            it.toDomainVocabularyFolder()
//        }
//        vocabularyList = emptyList()
//    )
}