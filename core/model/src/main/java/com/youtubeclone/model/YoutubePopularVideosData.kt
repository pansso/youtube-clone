package com.youtubeclone.model

import kotlinx.serialization.Serializable


@Serializable
data class YoutubePopularVideosData(
    val items: List<Item?>?,
) {
    @Serializable
    data class Item(
        val snippet: Snippet?,
        val statistics: Statistics?,
        val id: String?
    ) {
        @Serializable
        data class Snippet(
            val categoryId: String?,
            val channelId: String?,
            val channelTitle: String?,
            val description: String?,
            val publishedAt: String?,
            val tags: List<String?>?,
            val thumbnails: Thumbnails?,
            val title: String?
        )

        @Serializable
        data class Statistics(
            val commentCount: String?,
            val favoriteCount: String?,
            val likeCount: String?,
            val viewCount: String?
        )
    }
}