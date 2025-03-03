package com.example.search

import android.app.Activity
import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.youtubeclone.designsystem.White
import timber.log.Timber

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchText by viewModel.searchText.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTopBar(
            searchText = searchText,
            onSearchTextChanged = { viewModel.updateSearchText(it) },
            onBackPress = {
               backDispatcher?.onBackPressed()
            },
            onSearchClick = { viewModel.search(it) })
        SearchList(
            searchHistory = searchHistory,
            suggestions = suggestions,
            onSuggestionClicked = { viewModel.search(it) })
    }
}


@Composable
fun SearchTopBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onBackPress: () -> Unit,
    onSearchClick: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit){
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBackPress) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "back")
        }
        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
                .focusRequester(focusRequester),
            singleLine = true,
            placeholder = { Text("Search...") },
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSearchClick(searchText)
                }
            )
        )
    }
}

@Composable
fun SearchList(
    searchHistory: List<String?>?,
    suggestions: List<String?>?,
    onSuggestionClicked: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {

        searchHistory?.let {
            items(searchHistory.size) { history ->
                searchHistory[history]?.let {
                    Row(
                        modifier = Modifier
                            .clickable {
                                searchHistory[history]?.let { it ->
                                    onSuggestionClicked(it)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.ThumbUp, contentDescription = "searchHistoryIcon")
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = White
                        )
                    }
                }
                HorizontalDivider()
            }
        }

        suggestions?.let {
            items(suggestions.size) { suggestion ->
                suggestions[suggestion]?.let { it ->
                    Row(
                        modifier = Modifier
                            .clickable { suggestions[suggestion]?.let { it -> onSuggestionClicked(it) } },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "suggestionsIcon")
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = White
                        )
                    }
                    HorizontalDivider()
                }
            }
        }

    }
}

@Preview
@Composable
fun preview() {
    Column {
        SearchTopBar(
            searchText = "asddfv 토피넛라떼 검색해보기 테스트페이지1234123412341234123412341234123412341234123412341234",
            onSearchTextChanged = {},
            onBackPress = {},
            onSearchClick = {})
        SearchList(
            searchHistory = listOf("1번기록", "2번기록기록", "3번기록기기기"),
            suggestions = listOf("추천1", "추천리스트2", "추천3"),
            onSuggestionClicked = {})
    }

}
