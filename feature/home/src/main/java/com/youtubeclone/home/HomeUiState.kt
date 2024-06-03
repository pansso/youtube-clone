package com.youtubeclone.home

import com.youtubeclone.model.YoutubePopularVideosData

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val youtubeData: YoutubePopularVideosData):HomeUiState
    data class Error<T>(val e : T?):HomeUiState
}