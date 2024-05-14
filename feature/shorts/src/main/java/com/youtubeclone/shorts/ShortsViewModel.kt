package com.youtubeclone.shorts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youtubeclone.common.Error
import com.youtubeclone.common.Result
import com.youtubeclone.common.asResult
import com.youtubeclone.domain.usecase.GetVideosUseCase
import com.youtubeclone.model.YoutubeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class ShortsViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase
) : ViewModel() {

    private val _uiStateLiveData: MutableLiveData<ShortsUiState> = MutableLiveData()
    val uiStateLiveData: LiveData<ShortsUiState> = _uiStateLiveData

    private val isLoading = AtomicBoolean(false)

    private val random = listOf("a","b","C","d","e","f","g","h")

    fun fetchVideoData() {
        viewModelScope.launch {
            getShortsVideData("shorts 요리")
                .asResult()
                .collect {
                    when (it) {
                        is Result.Loading -> {
                            _uiStateLiveData.value = ShortsUiState.Loading
                        }

                        is Result.Success -> {
                            val videoData = it.data
                            _uiStateLiveData.value = ShortsUiState.Success(videoData)
                            Timber.d("sjh data = ${it.data}")
                        }

                        is Result.Error -> {
                            _uiStateLiveData.value = ShortsUiState.Error(it.Error)
                        }
                    }
                }
        }
    }

    fun moreVideData() {
        if (isLoading.getAndSet(true)) return
        viewModelScope.launch {
            getShortsVideData("shorts 요리 ${random.random()}")
                .asResult()
                .collect {
                    when (it) {
                        is Result.Loading -> {
                            _uiStateLiveData.value = ShortsUiState.Loading
                        }

                        is Result.Success -> {
                            val videoData = it.data
                            _uiStateLiveData.value = ShortsUiState.Success(videoData)
                            Timber.d("sjh data = ${it.data}")
                            viewModelScope.launch {
                                delay(500)
                                isLoading.set(false)
                            }
                        }

                        is Result.Error -> {
                            _uiStateLiveData.value = ShortsUiState.Error(it.Error)
                            viewModelScope.launch {
                                delay(500)
                                isLoading.set(false)
                            }
                        }
                    }
                }
        }
    }

    private fun getShortsVideData(q: String): Flow<YoutubeData> {
        return flow {
            val youtubeData = getVideosUseCase.invoke(q = q)
            emit(youtubeData)
        }
    }

}