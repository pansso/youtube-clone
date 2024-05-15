package com.youtubeclone.data.repository

import com.youtubeclone.data.model.YoutubeDataResponse
import com.youtubeclone.model.YoutubeData
import kotlinx.coroutines.flow.Flow

interface YoutubeRepository {
    suspend fun getVideos(q: String): YoutubeData

    suspend fun getSearchSuggestions(q: String): Flow<List<String?>?>
}