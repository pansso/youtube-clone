package com.youtubeclone.shorts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.youtubeclone.common.BaseFragment
import com.youtubeclone.model.YoutubeItem
import com.youtubeclone.shorts.adapter.ShortsAdapter
import com.youtubeclone.shorts.adapter.ShortsInterface
import com.youtubeclone.shorts.databinding.FragmentShortsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import kotlin.math.roundToInt


@AndroidEntryPoint
class ShortsFragment : BaseFragment<FragmentShortsBinding>(FragmentShortsBinding::inflate) {

    private val viewModel: ShortsViewModel by viewModels()


    private val adapter by lazy {
        ShortsAdapter(object : ShortsInterface {
            override val owner: LifecycleOwner
                get() = this@ShortsFragment
            override val observeScrollEvent: Flow<Int>
                get() = observeScrollEvent()
        })
    }

    private val pagerSnapHelper = PagerSnapHelper()

    private val linearLayoutManager = LinearLayoutManager(
        this.context,
        RecyclerView.VERTICAL,
        false
    )

    private val scrollEvent = MutableStateFlow(0)

    private fun observeScrollEvent(): Flow<Int> {
        return scrollEvent.asStateFlow()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()

        viewModel.fetchVideoData()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        binding.rvShorts.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        binding.rvShorts.layoutManager = linearLayoutManager
        binding.rvShorts.adapter = adapter
        binding.rvShorts.addScrollVisiblePositionListener()
        pagerSnapHelper.attachToRecyclerView(binding.rvShorts)
    }

    private fun initObserve() = with(viewModel) {
        uiStateLiveData.observe(viewLifecycleOwner) { it ->
            when (it) {
                is ShortsUiState.Loading -> {}
                is ShortsUiState.Success -> {
                    val list = it.youtubeData.items ?: emptyList()
                    adapter.submitList(adapter.currentList.toMutableList().apply {
                        addAll(list)
                    })
                }

                is ShortsUiState.Error<*> -> {}
            }
        }
    }

    private fun RecyclerView.addScrollVisiblePositionListener() {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollVisiblePositionEvent(recyclerView)
                pagingEvent(recyclerView)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }

    private fun scrollVisiblePositionEvent(recyclerView: RecyclerView) {
        val verticalScrollExtent = recyclerView.computeVerticalScrollExtent()
        val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
        if (verticalScrollExtent == 0) {
            return
        }
        val playablePosition = (recyclerView.computeVerticalScrollOffset()
            .toDouble() / verticalScrollExtent).roundToInt()
        if (scrollEvent.value != playablePosition) {
            scrollEvent.value = playablePosition
        }
    }

    private fun pagingEvent(recyclerView: RecyclerView) {
        val visibleItemCount = linearLayoutManager.childCount
        val totalItemCount = linearLayoutManager.itemCount
        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()

        // 스크롤이 마지막 아이템에 도달했는지 확인
        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
            // 추가 데이터를 가져오기 위한 API 요청 보내기
            viewModel.moreVideData()
        }
    }
}