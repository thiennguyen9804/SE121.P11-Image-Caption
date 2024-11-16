package com.example.se121p11new.di

import com.example.se121p11new.core.data.AppConstants
import com.example.se121p11new.data.remote.GcpApi
import com.example.se121p11new.data.repository.ImageRepositoryImpl
import com.example.se121p11new.data.remote.RemoteImageDataSource
import com.example.se121p11new.domain.repository.ImageRepository
import com.google.android.gms.common.internal.Constants
import com.google.firebase.Firebase
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.vertexAI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        generativeModel: GenerativeModel
    ): RemoteImageDataSource = RemoteImageDataSource(generativeModel)

    @Provides
    @Singleton
    fun provideImageRepository(
        remoteImageDataSource: RemoteImageDataSource
    ): ImageRepository = ImageRepositoryImpl(remoteImageDataSource)

    @Provides
    @Singleton
    fun provideGcpApi(): GcpApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.TRANSLATE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GcpApi::class.java)
    }
}