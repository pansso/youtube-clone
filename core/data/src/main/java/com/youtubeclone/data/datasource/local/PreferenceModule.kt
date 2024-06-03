package com.youtubeclone.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import com.youtubeclone.data.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class PreferenceModule {
    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("com.youtubeclone.shared", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    @Singleton
    fun providePreferencesModule(
        sharedPreferences: SharedPreferences,
        json: Json
    ): PreferencesRepository {
        return PreferencesRepository(sharedPreferences, json)
    }
}