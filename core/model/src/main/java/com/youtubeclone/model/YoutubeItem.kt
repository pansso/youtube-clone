package com.youtubeclone.model

import kotlinx.serialization.Serializable


data class YoutubeItem(
    val etag: String?,
    val id: Id?,
    val kind: String?,
    val snippet: Snippet?
)


data class Id(
    val channelId: String?,
    val kind: String?,
    val playlistId: String?,
    val videoId: String?
)


data class Snippet(
    val channelId: String?,
    val channelTitle: String?,
    val description: String?,
    val liveBroadcastContent: String?,
    val publishTime: String?,
    val publishedAt: String?,
    val thumbnails: Thumbnails?,
    val title: String?
)

@Serializable
data class Thumbnails(
    val default: Size?,
    val high: Size?,
    val medium: Size?
)
@Serializable
data class Size(
    val height: Int?,
    val url: String?,
    val width: Int?
)