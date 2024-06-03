package com.youtubeclone.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youtubeclone.domain.usecase.GetPopularVideosUseCase
import com.youtubeclone.domain.usecase.SharedPreferenceUseCase
import com.youtubeclone.model.YoutubePopularVideosData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularVideosUseCase: GetPopularVideosUseCase,
    private val preferenceUseCase: SharedPreferenceUseCase
) : ViewModel() {
    private val key = "HOME_FRAGMENT"
    private val disposables = CompositeDisposable()

    private val _uiStateLiveData: MutableLiveData<HomeUiState> = MutableLiveData()
    val uiStateLiveData: LiveData<HomeUiState> = _uiStateLiveData


    fun getPopularVideos() {
        disposables.add(
            getPopularVideosUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _uiStateLiveData.value = HomeUiState.Loading }
                .subscribe(
                    { data ->
                        _uiStateLiveData.value = HomeUiState.Success(data)
                        preferenceUseCase.savePopularVideo(key, data)
                    },
                    { error ->
                        _uiStateLiveData.value = HomeUiState.Error(error.localizedMessage)
                        val data = preferenceUseCase.loadPopularVideo(key)
                        data?.let { _uiStateLiveData.value = HomeUiState.Success(data) }
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}