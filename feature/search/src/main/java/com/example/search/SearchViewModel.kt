package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youtubeclone.domain.usecase.GetSearchSuggestionsUseCase
import com.youtubeclone.domain.usecase.SearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
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

        //검색기록
        viewModelScope.launch {
            searchText
                .flatMapLatest { text ->
                    searchHistoryUseCase.getAllSearchHistory()
                        .map { originList ->
                            //텍스트가 포함된 아이템리스트
                            val containsList = originList.filter { it ->
                                it.contains(text, ignoreCase = true)
                            }

                            //포함된 리스트중 검색값으로 시작하는 아이템들
                            val startedList = containsList.filter {
                                it.startsWith(text, ignoreCase = true)
                            }

                            val remainingList = containsList - startedList.toSet()

                            (startedList + remainingList).distinct().take(5)
                        }
                }
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    Timber.e(e, "Failed to get search history")
                }
                .collect { sortedList ->
                    _searchHistory.value = sortedList
                }
        }

        //api 검색 결과
        viewModelScope.launch {
            searchText
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { it ->
                    getSearchSuggestionsUseCase(it)
                }.catch {
                    Timber.e("suggestions error data = ${it.localizedMessage}")
                }
                .collect {
                    _suggestions.value = it
                }
        }
    }

    fun search(text: String) {
        saveSearchHistory(text)
    }

    private fun saveSearchHistory(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryUseCase.getAllSearchHistory()
                .firstOrNull()
                ?.let { list ->
                    val duplicateItem = list.find { it == text }
                    if (duplicateItem != null) {
                        searchHistoryUseCase.deleteSearchHistory(duplicateItem)
                    }
                    searchHistoryUseCase.saveSearchHistory(text)
                }
        }

    }

}