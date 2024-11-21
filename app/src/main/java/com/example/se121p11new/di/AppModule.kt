package com.example.se121p11new.di

import android.content.Context
import com.example.se121p11new.R
import com.example.se121p11new.core.data.AppConstants
import com.example.se121p11new.data.repository.ImageRepositoryImpl
import com.example.se121p11new.data.remote.RemoteImageDataSource
import com.example.se121p11new.domain.repository.ImageRepository
import com.google.android.gms.common.internal.Constants
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.vertexAI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
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
    fun provideImageRepository(
        remoteImageDataSource: RemoteImageDataSource
    ): ImageRepository = ImageRepositoryImpl(remoteImageDataSource)

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
    fun provideFirebaseAuth(
        @ApplicationContext context: Context
    ): FirebaseAuth = FirebaseAuth.getInstance()
}