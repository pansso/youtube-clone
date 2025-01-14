package com.youtubeclone.data.repository

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun saveSearchHistory(text: String)

//    fun getSearchHistory(keyword: String): Flow<List<String>>

    fun getAllSearchHistory(): Flow<List<String>>

    fun deleteSearchHistory(text: String)
}