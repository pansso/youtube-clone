package com.youtubeclone.youtubeplayer.ui

import androidx.annotation.IntDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    PlayerState.IDLE,
    PlayerState.ENDED,
    PlayerState.PLAYING,
    PlayerState.PAUSED,
    PlayerState.BUFFERING,
    PlayerState.CUED
)
internal annotation class PlayerState {
    companion object {
        const val IDLE = -1
        const val ENDED = 0
        const val PLAYING = 1
        const val PAUSED = 2
        const val BUFFERING = 3
        const val CUED = 4
    }
}