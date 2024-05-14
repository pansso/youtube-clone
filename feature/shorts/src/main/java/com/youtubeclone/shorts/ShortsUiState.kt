package com.youtubeclone.shorts

import com.youtubeclone.model.YoutubeData
import java.lang.Exception

sealed interface ShortsUiState {
    data object Loading : ShortsUiState
    data class Success(val youtubeData: YoutubeData):ShortsUiState
    data class Error<T>(val e : T?):ShortsUiState
}