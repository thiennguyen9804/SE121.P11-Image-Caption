package com.example.se121p11new.di

import android.content.Context
import com.example.se121p11new.R
import com.example.se121p11new.core.data.AppConstants
import com.example.se121p11new.data.local.LocalImageDataSource
import com.example.se121p11new.data.local.LocalVocabularyDataSource
import com.example.se121p11new.data.local.dao.ImageDao
import com.example.se121p11new.data.local.dao.VocabularyDao
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.Vocabulary
import com.example.se121p11new.data.remote.RemoteImageDataSource
import com.example.se121p11new.data.remote.RemoteVocabularyDataSource
import com.example.se121p11new.data.remote.api.VocabularyApi
import com.example.se121p11new.data.repository.ImageRepositoryImpl
import com.example.se121p11new.data.repository.VocabularyRepositoryImpl
import com.example.se121p11new.domain.repository.ImageRepository
import com.example.se121p11new.domain.repository.VocabularyRepository
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.google.firebase.Firebase
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.vertexAI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideLocalImageDataSource(
        imageDao: ImageDao
    ): LocalImageDataSource = LocalImageDataSource(imageDao)

    @Provides
    @Singleton
    fun provideRemoteVocabularyDataSource(
        api: VocabularyApi
    ): RemoteVocabularyDataSource = RemoteVocabularyDataSource(api)

    @Provides
    @Singleton
    fun provideLocalVocabularyDataSource(
        dao: VocabularyDao
    ) = LocalVocabularyDataSource(dao)

    @Provides
    @Singleton
    fun provideImageDao(
        realm: Realm
    ) = ImageDao(realm)

    @Provides
    @Singleton
    fun provideVocabularyDao(
        realm: Realm
    ) = VocabularyDao(realm)

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
        localVocabularyDataSource: LocalVocabularyDataSource
    ): VocabularyRepository = VocabularyRepositoryImpl(remoteVocabularyDataSource, localVocabularyDataSource)


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
    fun provideRealmDb(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                Image::class
            ))
            .deleteRealmIfMigrationNeeded()
            .name("app_db.realm")
            .build()

        val realm = Realm.open(configuration = config)

        return realm
    }

    @Provides
    @Singleton
    fun provideVocabularyApi(): VocabularyApi = Retrofit.Builder()
        .baseUrl(AppConstants.VOCABULARY_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(VocabularyApi::class.java)
}