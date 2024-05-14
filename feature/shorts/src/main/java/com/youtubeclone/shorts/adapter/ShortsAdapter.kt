package com.youtubeclone.shorts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.youtubeclone.designsystem.fadeIn
import com.youtubeclone.designsystem.fadeOut
import com.youtubeclone.model.YoutubeItem
import com.youtubeclone.shorts.databinding.LayoutShortsItemBinding
import com.youtubeclone.youtubeplayer.ui.PlayerCallback
import com.youtubeclone.youtubeplayer.ui.PlayerState
import com.youtubeclone.youtubeplayer.ui.YoutubePlayerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

interface ShortsInterface {
    val owner: LifecycleOwner
    val observeScrollEvent: Flow<Int>
}

class ShortsAdapter(private val shortsInterface: ShortsInterface) :
    ListAdapter<YoutubeItem, ShortsAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<YoutubeItem>() {
            override fun areItemsTheSame(oldItem: YoutubeItem, newItem: YoutubeItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: YoutubeItem, newItem: YoutubeItem): Boolean {
                return true
            }
        }
    ) {
    private var scrollEventJob: Job? = null

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        startHandleScrollEvent(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        stopHandleScrollEvent()
        holder.pause()
    }

    private var playJob: Job? = null
    private fun startHandleScrollEvent(holder: ViewHolder) {
        scrollEventJob = shortsInterface.owner.lifecycleScope.launch {
            shortsInterface.observeScrollEvent.collect {
                if (holder.bindingAdapterPosition == it) {
                    holder.play()
                    playJob?.cancel()
                    playJob = shortsInterface.owner.lifecycleScope.launch playJob@{
                        repeat(Int.MAX_VALUE) {
                            delay(200)
                            holder.play()
                            if (holder.isPlaying()) {
                                playJob?.cancel()
                                return@playJob
                            }
                        }
                    }
                } else {
                    playJob?.cancel()
                    holder.pause()
                }
            }
        }
    }

    private fun stopHandleScrollEvent() {
        scrollEventJob?.cancel()
        playJob?.cancel()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutShortsItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position) ?: return
        holder.onBind(data)
    }

    inner class ViewHolder(private val binding: LayoutShortsItemBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<YoutubeItem> {
        private val view: YoutubePlayerView = binding.shortsView
        override fun onBind(data: YoutubeItem) {
            binding.thumbnailView.load(data.snippet?.thumbnails?.high?.url)

            binding.thumbnailView.visibility = View.VISIBLE
            data.id?.videoId?.let {
                view.load(videoId = it, autoPlay = false, playerCallback = object : PlayerCallback {
                    override fun onPlayerReady() {
                        Timber.d("onPlayerReady")

                    }

                    override fun onPlayerStateChange(state: Int) {
                        Timber.d("onPlayerStateChange : $state")
                        when (state) {
                            PlayerState.PLAYING -> {
                                fadeOut(binding.thumbnailView, 500)
                            }

                            PlayerState.ENDED -> {
                                view.play()
                            }

                            PlayerState.PAUSED -> {
                                fadeIn(binding.thumbnailView, 500)
                            }

                            PlayerState.CUED -> {
//                                view.play()
                            }
                        }
                    }
                }, lifecycleOwner = shortsInterface.owner)

            }

        }

        fun play() {
            view.play()
        }

        fun pause() {
            view.pause()
        }

        fun release() {
            view.release()
        }

        fun isPlaying(): Boolean {
            return view.isPlaying()
        }

    }

    private interface Binder<T> {
        fun onBind(data: T)
    }
}