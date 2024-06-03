package com.youtubeclone.data.model.mapper

import com.youtubeclone.data.model.YoutubePopularVideosResponse
import com.youtubeclone.model.YoutubePopularVideosData

internal fun YoutubePopularVideosResponse.toData(): YoutubePopularVideosData =
    YoutubePopularVideosData(
        items = items?.map { it?.toData() }
    )

internal fun YoutubePopularVideosResponse.Item.toData(): YoutubePopularVideosData.Item =
    YoutubePopularVideosData.Item(
        snippet = snippet?.toData(),
        statistics = statistics?.toData(),
    )

internal fun YoutubePopularVideosResponse.Item.Snippet.toData(): YoutubePopularVideosData.Item.Snippet =
    YoutubePopularVideosData.Item.Snippet(
        categoryId = categoryId,
        channelId = channelId,
        channelTitle = channelTitle,
        description = description,
        publishedAt = publishedAt,
        tags = tags,
        thumbnails = thumbnails?.toData(),
        title = title,
    )

internal fun YoutubePopularVideosResponse.Item.Statistics.toData(): YoutubePopularVideosData.Item.Statistics =
    YoutubePopularVideosData.Item.Statistics(
        commentCount = commentCount,
        favoriteCount = favoriteCount,
        likeCount = likeCount,
        viewCount = viewCount
    )


