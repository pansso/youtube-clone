package com.youtubeclone.data.repository

import com.youtubeclone.data.model.YoutubeDataResponse
import com.youtubeclone.model.YoutubeData
import com.youtubeclone.model.YoutubePopularVideosData
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface YoutubeRepository {
    suspend fun getVideos(q: String): YoutubeData

    suspend fun getSearchSuggestions(q: String): Flow<List<String?>?>

    fun getPopularVideos(): Single<YoutubePopularVideosData>
}