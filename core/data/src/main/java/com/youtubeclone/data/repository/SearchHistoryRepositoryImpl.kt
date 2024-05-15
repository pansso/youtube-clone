package com.youtubeclone.data.repository

import com.youtubeclone.data.datasource.local.SearchHistoryDAO
import com.youtubeclone.data.model.SearchHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDAO: SearchHistoryDAO
) : SearchHistoryRepository {
    override suspend fun saveSearchHistory(text: String) {
        withContext(Dispatchers.IO) {
            searchHistoryDAO.insertSearchHistory(SearchHistoryEntity(text = text))
        }
    }


    override fun getSearchHistory(keyword: String): Flow<List<String>> {
        return searchHistoryDAO.getSearchHistory(keyword = keyword)
    }
}