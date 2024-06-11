package com.youtubeclone.home

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youtubeclone.home.databinding.LayoutHomeItemBinding
import com.youtubeclone.model.YoutubePopularVideosData
import dagger.hilt.android.internal.managers.ViewComponentManager.FragmentContextWrapper
import dagger.hilt.android.qualifiers.ActivityContext
import timber.log.Timber

class HomeAdapter() :
    ListAdapter<YoutubePopularVideosData.Item, HomeAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<YoutubePopularVideosData.Item>() {
        override fun areItemsTheSame(
            oldItem: YoutubePopularVideosData.Item,
            newItem: YoutubePopularVideosData.Item
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: YoutubePopularVideosData.Item,
            newItem: YoutubePopularVideosData.Item
        ): Boolean {
            return true
        }

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutHomeItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position) ?: return
        holder.onBind(data)
    }

    inner class ViewHolder(private val binding: LayoutHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<YoutubePopularVideosData.Item> {
        override fun onBind(data: YoutubePopularVideosData.Item) {
            binding.item = data
            binding.videoItem.setOnClickListener { openYouTubeLink(data.id) }
        }

        private fun openYouTubeLink(videoId: String?) {
            val context = binding.root.context
            val url = "https://www.youtube.com/watch?v=$videoId"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.google.android.youtube")

            //유튜브 설치되어있을때
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                //설치가 안되어있으면 웹뷰로 재생
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(webIntent)
            }
        }
    }


    private interface Binder<T> {
        fun onBind(data: T)
    }
}