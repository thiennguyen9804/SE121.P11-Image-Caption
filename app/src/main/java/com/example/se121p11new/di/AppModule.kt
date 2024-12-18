package com.example.se121p11new.di

import android.content.Context
import com.example.se121p11new.R
import com.example.se121p11new.core.data.AppConstants
import com.example.se121p11new.data.local.LocalImageDataSource
import com.example.se121p11new.data.local.LocalImageFolderDataSource
import com.example.se121p11new.data.local.LocalVocabularyDataSource
import com.example.se121p11new.data.local.LocalVocabularyFolderDataSource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.RealmImage
import com.example.se121p11new.data.local.realm_object.RealmImageFolder
import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import com.example.se121p11new.data.local.realm_object.Vocabulary
import com.example.se121p11new.data.remote.RemoteImageDataSource
import com.example.se121p11new.data.remote.RemoteVocabularyDataSource
import com.example.se121p11new.data.remote.api.VocabularyApi
import com.example.se121p11new.data.remote.dto.RealmDefinition
import com.example.se121p11new.data.remote.dto.RealmPartOfSpeech
import com.example.se121p11new.data.remote.dto.RealmPhrasalVerb
import com.example.se121p11new.data.remote.dto.RealmVocabulary
import com.example.se121p11new.data.repository.ImageFolderRepositoryImpl
import com.example.se121p11new.data.repository.ImageRepositoryImpl
import com.example.se121p11new.data.repository.VocabularyFolderRepositoryImpl
import com.example.se121p11new.data.repository.VocabularyRepositoryImpl
import com.example.se121p11new.domain.repository.ImageFolderRepository
import com.example.se121p11new.domain.repository.ImageRepository
import com.example.se121p11new.domain.repository.VocabularyFolderRepository
import com.example.se121p11new.domain.repository.VocabularyRepository
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.vertexAI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        val generativeModel = Firebase
            .vertexAI(location = "asia-southeast1")
            .generativeModel("gemini-1.5-flash")
        return generativeModel
    }

    @Provides
    @Singleton
    fun provideRemoteImageDataSource(
        generativeModel: GenerativeModel,
        translate: Translate
    ): RemoteImageDataSource = RemoteImageDataSource(generativeModel, translate)


    @Provides
    @Singleton
    fun provideRemoteVocabularyDataSource(
        api: VocabularyApi
    ): RemoteVocabularyDataSource = RemoteVocabularyDataSource(api)

    @Provides
    @Singleton
    fun provideLocalVocabularyDataSource(
        @Named("appDb") realm: Realm,
        @Named("cache") cache: Realm
    ): LocalVocabularyDataSource = LocalVocabularyDataSource(realm, cache)

    @Provides
    @Singleton
    fun provideLocalImageDataSource(
        @Named("appDb") realm: Realm
    ): LocalImageDataSource = LocalImageDataSource(realm)


    @Provides
    @Singleton
    fun provideImageRepository(
        remoteImageDataSource: RemoteImageDataSource,
        localImageDataSource: LocalImageDataSource
    ): ImageRepository = ImageRepositoryImpl(remoteImageDataSource, localImageDataSource)

    @Provides
    @Singleton
    fun provideVocabularyRepository(
        remoteVocabularyDataSource: RemoteVocabularyDataSource,
        localVocabularyDataSource: LocalVocabularyDataSource,
        remoteImageDataSource: RemoteImageDataSource
    ): VocabularyRepository = VocabularyRepositoryImpl(
        remoteVocabularyDataSource, localVocabularyDataSource, remoteImageDataSource
    )


    @Provides
    @Singleton
    fun provideTranslate(
        @ApplicationContext context: Context
    ): Translate =
        context.resources.openRawResource(R.raw.credentials).use { `is` ->
            val myCredentials = GoogleCredentials.fromStream(`is`)
            val translateOptions =
                TranslateOptions.newBuilder().setCredentials(myCredentials).build()
            translateOptions.service
        }

    @Provides
    @Singleton
    @Named("appDb")
    fun provideRealmDb(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                RealmImage::class,
                RealmVocabulary::class,
                RealmDefinition::class,
                RealmPhrasalVerb::class,
                RealmPartOfSpeech::class,
                RealmImageFolder::class,
                RealmVocabularyFolder::class,
            ))
            .deleteRealmIfMigrationNeeded()
            .name("app_db.realm")
            .build()

        val realm = Realm.open(configuration = config)
        return realm
    }

    @Provides
    @Singleton
    @Named("cache")
    fun provideCache(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                RealmVocabulary::class,
                RealmPhrasalVerb::class,
                RealmDefinition::class,
                RealmPartOfSpeech::class,
                RealmVocabularyFolder::class,
            ))
            .deleteRealmIfMigrationNeeded()
            .name("cache.realm")
            .build()

        val realm = Realm.open(configuration = config)
        return realm
    }

    @Provides
    @Singleton
    fun provideVocabularyApi(): VocabularyApi {
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(AppConstants.VOCABULARY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(VocabularyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalImageFolderDataSource(@Named("appDb") realm: Realm) : LocalImageFolderDataSource {
        return LocalImageFolderDataSource(realm)
    }

    @Provides
    @Singleton
    fun provideImageFolderRepository(
        localImageFolderDataSource: LocalImageFolderDataSource
    ): ImageFolderRepository {
        return ImageFolderRepositoryImpl(localImageFolderDataSource)
    }

    @Provides
    @Singleton
    fun provideLocalVocabularyFolderDataSource(@Named("appDb") realm: Realm) : LocalVocabularyFolderDataSource {
        return LocalVocabularyFolderDataSource(realm)
    }

    @Provides
    @Singleton
    fun provideVocabularyFolderRepository(
        localVocabularyFolderDataSource: LocalVocabularyFolderDataSource
    ): VocabularyFolderRepository {
        return VocabularyFolderRepositoryImpl(localVocabularyFolderDataSource)

    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideCloudStorage(): FirebaseStorage = FirebaseStorage.getInstance()
}