package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youtubeclone.domain.usecase.GetSearchSuggestionsUseCase
import com.youtubeclone.domain.usecase.SearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchSuggestionsUseCase: GetSearchSuggestionsUseCase,
    private val searchHistoryUseCase: SearchHistoryUseCase,
) : ViewModel() {

    //유저가 검색창에 입력한 텍스트
    private val _searchText: MutableStateFlow<String> = MutableStateFlow<String>("")
    val searchText: StateFlow<String>
        get() = _searchText.asStateFlow()

    //api로 받아온 목록
    private val _suggestions: MutableStateFlow<List<String?>?> =
        MutableStateFlow<List<String?>?>(emptyList())
    val suggestions: StateFlow<List<String?>?>
        get() = _suggestions.asStateFlow()

    //과거 검색기록
    private val _searchHistory: MutableStateFlow<List<String>> = MutableStateFlow<List<String>>(
        emptyList()
    )
    val searchHistory: StateFlow<List<String>>
        get() = _searchHistory.asStateFlow()

    init {
        debounceSearchText()
    }

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun debounceSearchText() {
        viewModelScope.launch {
            searchText
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { it ->
                    getSearchSuggestionsUseCase(it)
                }.catch {
                    // todo error catch event
                }
                .collect {
                    _suggestions.value = it
                }

            searchText
                .flatMapLatest { it ->
                    searchHistoryUseCase.getSearchHistory(it)
                }.catch {

                }.collect { searchList ->
                    _searchHistory.value = searchList
                }

        }
    }

    fun search(text: String) {
        viewModelScope.launch {
            //todo Search Api logic
            searchHistoryUseCase.saveSearchHistory(text)
        }
    }


}