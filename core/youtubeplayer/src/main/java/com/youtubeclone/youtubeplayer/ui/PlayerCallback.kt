package com.youtubeclone.youtubeplayer.ui

/**
 * Player에서 변화가 생길 때 알려주어야 할거 같은 정보만 콜백에 담았습니다
 */
interface PlayerCallback {
    fun onPlayerReady()
    fun onPlayerStateChange(@PlayerState state: Int)
}