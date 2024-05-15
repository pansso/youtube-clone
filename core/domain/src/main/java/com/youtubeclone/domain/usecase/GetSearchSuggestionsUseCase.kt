package com.youtubeclone.domain.usecase

import com.youtubeclone.data.repository.YoutubeRepository
import com.youtubeclone.model.YoutubeData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchSuggestionsUseCase @Inject constructor(
    private val youtubeRepository: YoutubeRepository,
) {
    suspend operator fun invoke(q: String): Flow<List<String?>?> {
        return youtubeRepository.getSearchSuggestions(q)
    }
}