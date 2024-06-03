package com.youtubeclone.domain.usecase

import com.youtubeclone.data.repository.PreferencesRepository
import com.youtubeclone.model.YoutubePopularVideosData
import javax.inject.Inject

class SharedPreferenceUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {
    fun savePopularVideo(key: String, data: YoutubePopularVideosData) {
        preferencesRepository.saveData(key, data)
    }

    fun loadPopularVideo(key: String): YoutubePopularVideosData? {
        return preferencesRepository.loadData(key)
    }


}