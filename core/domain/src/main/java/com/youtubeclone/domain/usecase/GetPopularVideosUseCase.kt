package com.youtubeclone.domain.usecase

import com.youtubeclone.data.repository.YoutubeRepository
import com.youtubeclone.model.YoutubeData
import com.youtubeclone.model.YoutubePopularVideosData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetPopularVideosUseCase @Inject constructor(
    private val youtubeRepository: YoutubeRepository,
) {
    operator fun invoke() : Single<YoutubePopularVideosData> {
        return youtubeRepository.getPopularVideos()
    }
}