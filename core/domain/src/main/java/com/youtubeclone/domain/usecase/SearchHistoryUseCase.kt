package com.youtubeclone.domain.usecase

import com.youtubeclone.data.repository.SearchHistoryRepository
import com.youtubeclone.data.repository.YoutubeRepository
import com.youtubeclone.model.YoutubeData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchHistoryUseCase @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) {
    fun getSearchHistory(keyword: String): Flow<List<String>> {
        return searchHistoryRepository.getSearchHistory(keyword = keyword)
    }

    suspend fun saveSearchHistory(text: String) {
        return searchHistoryRepository.saveSearchHistory(text = text)
    }
}