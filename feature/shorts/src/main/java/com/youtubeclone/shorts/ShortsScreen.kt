//package com.youtubeclone.shorts
//
//
//import android.content.Context
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.youtubeclone.designsystem.YoutubeBlack
//import com.youtubeclone.shorts.adapter.ShortsAdapter
//import com.youtubeclone.youtubeplayer.ui.YoutubePlayerView
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun ShortsScreen(
//    padding: PaddingValues
//) {
//    val viewModel: ShortsViewModel = hiltViewModel()
//    val context = LocalContext.current
////    val uiState = viewModel.uiState.collectAsState().value
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchVideoData()
//    }
//
////    ShortsComposableView(padding, uiState, context)
////    ShortsXmlView(padding, context, uiState)
//
//
//// 라이프사이클 관련된 소스는 job Cancel을 달아도 무한반복됩니다..ㅠ
////    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
////    val lifecycle = LocalLifecycleOwner.current.lifecycle
////    var job: Job? = null
////    job = lifecycleScope.launch {
////
////        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
////            viewModel.fetchVideoData()
////            job?.cancel()
////        }
////    }
//
//
////    val playerState = remember { mutableStateOf<String>("") }
////    val currentTime = remember { mutableStateOf<Long?>(0) }
////    val context = LocalContext.current
////    val lifecycleOwner = LocalLifecycleOwner.current
////    var job: Job? = null
////    var customYoutubePlayer: YoutubePlayerView? = null
////
////
////    val playerCallback = remember {
////        object : PlayerCallback {
////            override fun onPlayerReady() {
////                playerState.value = PlayBackState.STATE_READY.getName
////            }
////
////            override fun onPlayerStateChange(state: Int) {
////                when (state) {
////                    PlayBackState.STATE_IDLE.state -> playerState.value =
////                        PlayBackState.STATE_IDLE.getName
////
////                    PlayBackState.STATE_ENDED.state -> {
////                        playerState.value = PlayBackState.STATE_ENDED.getName
////                        job?.cancel()
////                    }
////
////                    PlayBackState.STATE_PLAYING.state -> {
////                        playerState.value = PlayBackState.STATE_PLAYING.getName
////
////                        job = lifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
////                            customYoutubePlayer?.let { youtubePlayer ->
////                                repeat(Int.MAX_VALUE) {
////                                    currentTime.value = youtubePlayer.getCurrentTime()
////                                    delay(200)
////                                }
////                            }
////                        }
////
////                    }
////
////                    PlayBackState.STATE_PAUSED.state -> {
////                        playerState.value = PlayBackState.STATE_PAUSED.getName
////                        job?.cancel()
////                    }
////
////                    PlayBackState.STATE_BUFFERING.state -> {
////                        playerState.value = PlayBackState.STATE_BUFFERING.getName
////                        job?.cancel()
////                    }
////
////                    PlayBackState.STATE_CUED.state -> playerState.value =
////                        PlayBackState.STATE_CUED.getName
////
////                    else -> throw IllegalArgumentException("일치하지 않은 상태값 입니다 $state")
////                }
////            }
////        }
////    }
////
////    DisposableEffect(key1 = lifecycleOwner) {
////        val observer = LifecycleEventObserver { _, event ->
////            when (event) {
////                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> {
////                    job?.cancel()
////                }
////                Lifecycle.Event.ON_DESTROY -> {
////                    job?.cancel()
////                    customYoutubePlayer?.release()
////                }
////
////                else -> {}
////            }
////        }
////        lifecycleOwner.lifecycle.addObserver(observer)
////
////        onDispose {
////            lifecycleOwner.lifecycle.removeObserver(observer)
////        }
////    }
////
////    customYoutubePlayer = remember {
////        YoutubePlayerView
////            .Builder(context)
////            .setVideoId("LDoZ4vOG8tM")
////            .setLifecycleOwner(lifecycleOwner)
////            .setPlayerCallback(playerCallback)
////            .build()
////    }
////
////    Column(modifier = Modifier
////        .padding(padding)
////        .fillMaxSize()) {
////        Column(
////            modifier = Modifier
////                .fillMaxWidth()
////                .fillMaxHeight()
////        ) {
////            customYoutubePlayer.YoutubePlayerComposeView(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .height(300.dp)
////            )
////
////            Text(
////                text = "State = ${playerState.value} , Time = ${currentTime.value}",
////                color = YoutubeBlack
////            )
////            Row {
////                Button(onClick = {
////                    currentTime.value = customYoutubePlayer.getCurrentTime()
////                }) {
////                    Text(text = "getCurrentTime")
////                }
////                Button(onClick = {
////                    customYoutubePlayer.play()
////                }) {
////                    Text(text = "PlayButton")
////                }
////                Button(onClick = {
////                    customYoutubePlayer.pause()
////                }) {
////                    Text(text = "StopButton")
////                }
////            }
////        }
////    }
//}
//
//@Composable
//fun ShortsXmlView(padding: PaddingValues, context: Context, uiState: ShortsUiState) {
//
//    /****************
//     * ## Xml wrapper
//     ****************/
//
//    val recyclerView = remember {
//        RecyclerView(context).apply {
//            layoutManager = LinearLayoutManager(context)
//        }
//    }
//
//    val owner = LocalLifecycleOwner.current
//
////    val adapter = remember {
////        ShortsAdapter(owner = owner,recyclerView)
////    }
//
////    Column(modifier = Modifier
////        .padding(padding)
////        .fillMaxSize().background(YoutubeBlack)) {
////        AndroidView(
////            factory = { recyclerView }, modifier = Modifier
////                .fillMaxWidth()
////                .fillMaxHeight()
////        ) { view ->
////            when (uiState) {
////                is ShortsUiState.Success -> {
////                    view.adapter = adapter
////                    adapter.submitList(uiState.youtubeData.items)
////                }
////
////                is ShortsUiState.Loading -> {
////
////                }
////
////                is ShortsUiState.Error<*> -> {
////
////                }
////            }
////        }
////    }
//
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun ShortsComposableView(padding: PaddingValues, uiState: ShortsUiState, context: Context) {
//    /***********************************************
//     *  ## Compose view 로 구현
//     ****************************************/
//    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
//    val lazyListState = rememberLazyListState()
//
//    Column(
//        modifier = Modifier
//            .padding(padding)
//            .fillMaxSize()
//    ) {
//        when (uiState) {
//            is ShortsUiState.Success -> {
//                var currentCenteredWebViewIndex by remember { mutableStateOf(-1) }
//                LazyColumn(
//                    state = lazyListState,
//                    flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
//                ) {
//                    items(uiState.youtubeData.items!!.size) {
//                        val data = uiState.youtubeData.items!![it]?.id?.videoId
//                        val customYoutubePlayer: YoutubePlayerView = remember {
//                            YoutubePlayerView
//                                .Builder(context)
//                                .setVideoId(data!!)
//                                .build()
//                        }
//
//                        customYoutubePlayer.YoutubePlayerComposeView(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(screenHeight)
//                                .padding(
//                                    top = padding.calculateTopPadding(),
//                                    bottom = padding.calculateBottomPadding()
//                                )
//                        )
//                        val firstVisibleItemIndex =
//                            remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value
//                        val lastVisibleItemIndex =
//                            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
//
//                        if (firstVisibleItemIndex == lastVisibleItemIndex && currentCenteredWebViewIndex != firstVisibleItemIndex) {
//                            currentCenteredWebViewIndex = firstVisibleItemIndex
//                            // 가운데에 있는 WebView의 play() 메서드 호출
//                            customYoutubePlayer.play()
//                        } else if (currentCenteredWebViewIndex != -1) {
//                            // 가운데에 없는 WebView의 stop() 메서드 호출
//                            customYoutubePlayer.pause()
//                            currentCenteredWebViewIndex = -1
//                        }
//                    }
//                }
//
//            }
//
//            is ShortsUiState.Loading -> {
//
//            }
//
//            is ShortsUiState.Error<*> -> {
//
//            }
//        }
//    }
//}
