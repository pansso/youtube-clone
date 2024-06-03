package com.youtubeclone.data.repository

import com.youtubeclone.data.api.YoutubeApi
import com.youtubeclone.data.model.YoutubeDataResponse
import com.youtubeclone.data.model.mapper.toData
import com.youtubeclone.model.YoutubeData
import com.youtubeclone.model.YoutubePopularVideosData
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

internal class YoutubeRepositoryImpl @Inject constructor(
    private val youtubeApi: YoutubeApi,
) : YoutubeRepository {
    override suspend fun getVideos(q: String): YoutubeData {
        return youtubeApi.getVideos(q = q).toData()
    }

    override suspend fun getSearchSuggestions(q: String): Flow<List<String?>?> {
        val data = youtubeApi.getVideos(q = q).toData()
        return flow { emit(data.items?.map { it?.snippet?.title }) }
    }

    override fun getPopularVideos(): Single<YoutubePopularVideosData> {
        return youtubeApi.getPopularVideos()
            .subscribeOn(Schedulers.io())
            .map { it.toData() }
    }
}