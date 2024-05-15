package com.youtubeclone.data.datasource

import com.youtubeclone.data.api.YoutubeApi
import com.youtubeclone.data.repository.SearchHistoryRepository
import com.youtubeclone.data.repository.SearchHistoryRepositoryImpl
import com.youtubeclone.data.repository.YoutubeRepository
import com.youtubeclone.data.repository.YoutubeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Binds
    abstract fun bindsYoutubeRepository(
        repositoryImpl: YoutubeRepositoryImpl
    ) : YoutubeRepository

    @Binds
    abstract fun bindSearchHistoryRepository(
        repositoryImpl: SearchHistoryRepositoryImpl
    ) : SearchHistoryRepository

}