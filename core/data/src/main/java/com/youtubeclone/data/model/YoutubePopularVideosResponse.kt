package com.youtubeclone.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YoutubePopularVideosResponse(
    @SerialName("items")
    val items: List<Item?>?,
) {
    @Serializable
    data class Item(
        @SerialName("snippet")
        val snippet: Snippet?,
        @SerialName("statistics")
        val statistics: Statistics?
    ) {
        @Serializable
        data class Snippet(
            @SerialName("categoryId")
            val categoryId: String?,
            @SerialName("channelId")
            val channelId: String?,
            @SerialName("channelTitle")
            val channelTitle: String?,
            @SerialName("description")
            val description: String?,
            @SerialName("publishedAt")
            val publishedAt: String?,
            @SerialName("tags")
            val tags: List<String?>?,
            @SerialName("thumbnails")
            val thumbnails: ThumbnailsResponse?,
            @SerialName("title")
            val title: String?
        ) {

        }

        @Serializable
        data class Statistics(
            @SerialName("commentCount")
            val commentCount: String?,
            @SerialName("favoriteCount")
            val favoriteCount: String?,
            @SerialName("likeCount")
            val likeCount: String?,
            @SerialName("viewCount")
            val viewCount: String?
        )
    }
}