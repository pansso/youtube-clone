package com.youtubeclone.data.api

import com.youtubeclone.data.BuildConfig
import com.youtubeclone.data.model.YoutubeDataResponse
import com.youtubeclone.data.model.YoutubePopularVideosResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface YoutubeApi {
    @GET("youtube/v3/search")
    suspend fun getVideos(
        @Query("part") part: String = "snippet",
        @Query("q") q: String,  //검색할 키워드
        @Query("maxResult") maxResult: Int = 10,
        @Query("type") type: String = "video",
        @Query("videoEmbeddable") videoEmbeddable: String = "true",
        @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY,
    ): YoutubeDataResponse

    @GET("youtube/v3/videos")
    fun getPopularVideos(
        @Query("part") part: String = "snippet,statistics",
        @Query("chart") chart: String = "mostPopular",
        @Query("maxResult") maxResult: Int = 10,
        @Query("regionCode") regionCode: String = "kr",
        @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY,
    ): Single<YoutubePopularVideosResponse>


}