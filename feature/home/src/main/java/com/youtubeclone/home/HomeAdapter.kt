package com.youtubeclone.home

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.youtubeclone.home.databinding.LayoutHomeItemBinding
import com.youtubeclone.model.YoutubeItem
import com.youtubeclone.model.YoutubePopularVideosData

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
        }

    }

    private interface Binder<T> {
        fun onBind(data: T)
    }
}