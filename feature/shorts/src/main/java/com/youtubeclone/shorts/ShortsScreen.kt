package com.youtubeclone.shorts


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.view.get
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import com.youtubeclone.designsystem.YoutubeBlack
import com.youtubeclone.youtubeplayer.ui.PlayBackState
import com.youtubeclone.youtubeplayer.ui.PlayerCallback
import com.youtubeclone.youtubeplayer.ui.YoutubePlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun ShortsScreen() {
    val playerState = remember { mutableStateOf<String>("") }
    val currentTime = remember { mutableStateOf<Long?>(0) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var job: Job? = null
    var customYoutubePlayer: YoutubePlayerView? = null

    val playerCallback = remember {
        object : PlayerCallback {
            override fun onPlayerReady() {
                playerState.value = PlayBackState.STATE_READY.getName
            }

            override fun onPlayerStateChange(state: Int) {
                when (state) {
                    PlayBackState.STATE_IDLE.state -> playerState.value =
                        PlayBackState.STATE_IDLE.getName

                    PlayBackState.STATE_ENDED.state -> {
                        playerState.value = PlayBackState.STATE_ENDED.getName
                        job?.cancel()
                    }

                    PlayBackState.STATE_PLAYING.state -> {
                        playerState.value = PlayBackState.STATE_PLAYING.getName

                        job = lifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                            customYoutubePlayer?.let { youtubePlayer ->
                                repeat(Int.MAX_VALUE) {
                                    currentTime.value = youtubePlayer.getCurrentTime()
                                    delay(200)
                                }
                            }
                        }

                    }

                    PlayBackState.STATE_PAUSED.state -> {
                        playerState.value = PlayBackState.STATE_PAUSED.getName
                        job?.cancel()
                    }

                    PlayBackState.STATE_BUFFERING.state -> {
                        playerState.value = PlayBackState.STATE_BUFFERING.getName
                        job?.cancel()
                    }

                    PlayBackState.STATE_CUED.state -> playerState.value =
                        PlayBackState.STATE_CUED.getName

                    else -> throw IllegalArgumentException("일치하지 않은 상태값 입니다 $state")
                }
            }
        }
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> {
                    job?.cancel()
                }
                Lifecycle.Event.ON_DESTROY -> {
                    job?.cancel()
                    customYoutubePlayer?.release()
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    customYoutubePlayer = remember {
        YoutubePlayerView
            .Builder(context)
            .setVideoId("LDoZ4vOG8tM")
            .setLifecycleOwner(lifecycleOwner)
            .setPlayerCallback(playerCallback)
            .build()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            customYoutubePlayer.YoutubePlayerComposeView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Text(
                text = "State = ${playerState.value} , Time = ${currentTime.value}",
                color = YoutubeBlack
            )
            Row {
                Button(onClick = {
                    currentTime.value = customYoutubePlayer.getCurrentTime()
                }) {
                    Text(text = "getCurrentTime")
                }
                Button(onClick = {
                    customYoutubePlayer.play()
                }) {
                    Text(text = "PlayButton")
                }
                Button(onClick = {
                    customYoutubePlayer.pause()
                }) {
                    Text(text = "StopButton")
                }
            }
        }
    }
}
