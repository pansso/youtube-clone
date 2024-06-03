package com.youtubeclone.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.youtubeclone.common.BaseFragment
import com.youtubeclone.home.databinding.FragmentHomeBinding
import com.youtubeclone.model.YoutubePopularVideosData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val homeAdapter: HomeAdapter by lazy { HomeAdapter() }

    private val linearLayoutManager = LinearLayoutManager(
        this.context,
        RecyclerView.VERTICAL,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()
        viewModel.getPopularVideos()
    }

    private fun initRecyclerView() {
        binding.rvHome.layoutManager = linearLayoutManager
        binding.rvHome.adapter = homeAdapter
    }

    private fun initObserve() = with(viewModel) {
        uiStateLiveData.observe(viewLifecycleOwner) { it ->
            when (it) {
                is HomeUiState.Loading -> {}
                is HomeUiState.Success -> {
                    homeAdapter.submitList(it.youtubeData.items)
                }
                is HomeUiState.Error<*> -> {}
            }
        }
    }
}
