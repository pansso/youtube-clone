package com.youtubeclone.youtubeplayer.ui

import java.util.Locale

enum class PlayBackState(val state: Int) {
    STATE_IDLE(-1),
    STATE_ENDED(0),
    STATE_PLAYING(1),
    STATE_PAUSED(2),
    STATE_BUFFERING(3),
    STATE_CUED(4),
    STATE_READY(5);

    val getName: String
        get() = this.name.uppercase(Locale.ROOT)
}