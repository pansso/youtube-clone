import android.webkit.JavascriptInterface
import com.youtubeclone.youtubeplayer.ui.PlayerCallback
import com.youtubeclone.youtubeplayer.ui.PlayerState

class PlayerEventHandler(private val playerCallback: PlayerCallback?) {
    private var playerState: Int? = null
    private var currentTime: Double? = null
    private var duration: Double? = null

    @JavascriptInterface
    fun onPlayerStateChange(@PlayerState state: Int) {
        playerState = state
        playerCallback?.onPlayerStateChange(state)
    }

    @JavascriptInterface
    fun setCurrentTime(time: Double) {
        currentTime = time
    }

    @JavascriptInterface
    fun setDuration(duration: Double) {
        this.duration = duration
    }

    @JavascriptInterface
    fun onPlayerStateReady() {
        playerCallback?.onPlayerReady()
    }

    fun getPlayerState(): Int? {
        return playerState
    }

    fun getCurrentTime(): Double? {
        return currentTime
    }

    fun getDuration(): Double? {
        return duration
    }
}