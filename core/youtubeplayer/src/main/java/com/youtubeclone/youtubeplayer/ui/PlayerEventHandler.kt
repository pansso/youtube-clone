package com.youtubeclone.youtubeplayer.ui

import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface

class PlayerEventHandler {
    private var playerState: Int? = null
    private var currentTime: Long? = null
    private var duration: Double? = null
    private var isPlaying: Boolean = false
    private var playerCallback: PlayerCallback? = null

    @JavascriptInterface
    fun onPlayerStateChange(@PlayerState state: Int) {
        Handler(Looper.getMainLooper()).post {
            playerState = state
            isPlaying = state == PlayerState.PLAYING
            playerCallback?.onPlayerStateChange(state)
        }
    }

    @JavascriptInterface
    fun setCurrentTime(time: Double) {
        Handler(Looper.getMainLooper()).post {
            currentTime = (time * 1000).toLong()
        }
    }

    @JavascriptInterface
    fun setDuration(duration: Double) {
        Handler(Looper.getMainLooper()).post {
            this.duration = duration
        }
    }

    @JavascriptInterface
    fun onPlayerStateReady() {
        Handler(Looper.getMainLooper()).post {
            playerCallback?.onPlayerReady()
        }
    }

    fun setPlayerCallback(callback: PlayerCallback?) {
        playerCallback = callback
    }

    fun getPlayerState(): Int? {
        return playerState
    }

    fun getCurrentTime(): Long? {
        return currentTime
    }

    fun getDuration(): Double? {
        return duration
    }

    fun isPlaying(): Boolean {
        return isPlaying
    }
}