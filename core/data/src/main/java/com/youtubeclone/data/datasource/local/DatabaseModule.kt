package com.youtubeclone.data.datasource.local

import android.content.Context
import androidx.room.Room
import com.youtubeclone.data.repository.SearchHistoryRepository
import com.youtubeclone.data.repository.SearchHistoryRepositoryImpl
import com.youtubeclone.data.repository.YoutubeRepository
import com.youtubeclone.data.repository.YoutubeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): YoutubeCloneDatabase{
        return YoutubeCloneDatabase.getDBInstance(appContext)
    }

    @Provides
    @Singleton
    fun provideSearchHistoryDao(db: YoutubeCloneDatabase) : SearchHistoryDAO {
        return db.searchHistoryDao
    }

}