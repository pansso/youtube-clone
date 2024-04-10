package com.youtubeclone.youtubeplayer.ui

import PlayerEventHandler
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.youtubeclone.youtubeplayer.BuildConfig


@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
class YoutubePlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : WebView(context, attrs, defStyleAttr) {
    /***********************
     * 초기 설정
     ***********************/
    private var eventHandler: PlayerEventHandler? = null

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.mediaPlaybackRequiresUserGesture = false
        if (!BuildConfig.DEBUG) {
            setWebContentsDebuggingEnabled(true)
        }
        webChromeClient = WebChromeClient()
    }

    /**
     * @param videoId 키값이라 빠지면 에러반환
     * @param seekTo 사용시 자동재생 설정됨
     * @param lifecycleOwner 사용시 stop 이후 동영상을 정지
     */
    data class Builder(
        private val context: Context,
        private var videoId: String? = null,
        private var seekTo: Int? = 0,
        private var autoPlay: Boolean = false,
        private var playerCallback: PlayerCallback? = null,
        private var lifecycleOwner: LifecycleOwner? = null
    ) {
        fun setVideoId(videoId: String) = apply { this.videoId = videoId }
        fun setSeekTo(seekTo: Int) = apply { this.seekTo = seekTo }
        fun setAutoPlay(autoPlay: Boolean) = apply { this.autoPlay = autoPlay }
        fun setPlayerCallback(playerCallback: PlayerCallback) =
            apply { this.playerCallback = playerCallback }

        fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) =
            apply { this.lifecycleOwner = lifecycleOwner }

        fun build(): YoutubePlayerView {
            val videoId = videoId
            requireNotNull(videoId) { "video Id가 없습니다." }
            val player = YoutubePlayerView(context, null, 0).apply {
                initialize(
                    videoId = videoId,
                    seekTo = seekTo,
                    autoPlay = autoPlay,
                    lifecycleOwner = lifecycleOwner
                )
                eventHandler = PlayerEventHandler(playerCallback)
                addJavascriptInterface(eventHandler!!, "playerEventHandler")
            }
            return player
        }
    }

    private fun initialize(
        videoId: String,
        seekTo: Int? = 0,
        autoPlay: Boolean = false,
        lifecycleOwner: LifecycleOwner?
    ) {
        val htmlData = getHTMLData(videoId, seekTo, autoPlay)
        loadDataWithBaseURL("https://www.youtube.com", htmlData, "text/html", "UTF-8", null)

        lifecycleOwner?.let {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    ON_STOP -> {
                        pause()
                    }

                    else -> Unit
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == ON_DESTROY) {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            })
        }
    }

    /***********************
     * player control
     ***********************/
    private fun setCurrentTime() = evaluateWebViewFunction("window.getCurrentTime();")

    fun getPlayerState(): Int? = eventHandler?.getPlayerState()


    fun getCurrentTime(): Double? = eventHandler?.getCurrentTime()


    fun play() = evaluateWebViewFunction("window.playVideo();")

    fun pause() = evaluateWebViewFunction("window/pauseVideo();")


    private fun evaluateWebViewFunction(
        script: String,
        callback: ((String) -> Unit)? = null,
    ) {
        return this.evaluateJavascript(script, callback)
    }

    //코루틴 으로 사용한다면 이걸로
    private suspend fun evaluateJavaScriptWithCoroutine(script: String): String {
        return suspendCoroutine { cont ->
            this.evaluateJavascript(script) { result ->
                cont.resume(result)
            }
        }
    }

    /***********************
     * view 선언부
     ***********************/
    @Composable
    fun YoutubePlayerComposeView(modifier: Modifier) {
        AndroidView(
            modifier = modifier,
            factory = { _ ->
                this
            }
        )
    }

    private fun getHTMLData(
        videoId: String,
        seekTo: Int?,
        autoPlay: Boolean,
    ): String {
        return """"
        <html>
            <body">
               <div id="player"></div>
                <script>
                    var player;
                    var interval
                    function onYouTubeIframeAPIReady() {
                        player = new YT.Player('player', {
                            height: '100%',
                            width: '100%',
                            videoId: '$videoId',
                            playerVars: {
                                'playsinline': 1
                            },
                            events: {
                                'onReady': onPlayerReady,
                                'onStateChange': onPlayerStateChange,
                            }
                        });
                    }
                    function onPlayerReady(event) {
                        playerEventHandler.onPlayerStateReady();
                        getDuration();
                        if ($seekTo != 0) seekTo($seekTo);
                        if ($autoPlay) playVideo();
                    }
                    
                    function onPlayerStateChange(event) {
                    
                         playerEventHandler.onPlayerStateChange(event.data);
                         
                         if (event.data == YT.PlayerState.PLAYING) {

                            clearInterval(interval);

                            interval = setInterval(function() {
                                getCurrentTime();
                            }, 200);
                         } else if (event.data == YT.PlayerState.PAUSED) {
                            clearInterval(interval);
                         } else if (event.data == YT.PlayerState.ENDED) {
                            clearInterval(interval);
                         }
                    }
                    function getCurrentTime() {
                        if (player.getCurrentTime() <= 0) {
                            clearInterval(interval);
                        } else {
                            playerEventHandler.setCurrentTime(player.getCurrentTime());
                        }
                    }
                    function getDuration() {
                        playerEventHandler.setDuration(player.getDuration());
                    }
                    function seekTo(time) {
                        player.seekTo(time, true);
                    }
                      function playVideo() {
                        player.playVideo();
                    }
                    function pauseVideo() {
                        player.pauseVideo();
                    }
                </script>
                <script src="https://www.youtube.com/iframe_api"></script>
            </body>
        </html>
    """".trimIndent()
    }
}