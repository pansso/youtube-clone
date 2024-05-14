package com.youtubeclone.shorts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.youtubeclone.model.YoutubeData
import com.youtubeclone.model.YoutubeItem
import com.youtubeclone.shorts.adapter.ShortsAdapter
import com.youtubeclone.shorts.adapter.ShortsInterface
import com.youtubeclone.shorts.databinding.ActivityShortsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.roundToInt

@AndroidEntryPoint
class ShortsActivity : AppCompatActivity() {


    private val viewModel: ShortsViewModel by viewModels()

    private val binding: ActivityShortsBinding by lazy {
        ActivityShortsBinding.inflate(
            layoutInflater
        )
    }

    private val adapter by lazy {
        ShortsAdapter(object : ShortsInterface {
            override val owner: LifecycleOwner
                get() = this@ShortsActivity
            override val observeScrollEvent: Flow<Int>
                get() = observeScrollEvent()
        })
    }

    private val pagerSnapHelper = PagerSnapHelper()

    private val linearLayoutManager = LinearLayoutManager(
        this,
        RecyclerView.VERTICAL,
        false
    )

    private val scrollEvent = MutableStateFlow(0)

    private fun observeScrollEvent(): Flow<Int> {
        return scrollEvent.asStateFlow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

    private val currentList: MutableList<YoutubeItem?> = emptyList<YoutubeItem>().toMutableList()

    private fun initObserve() = with(viewModel) {
        uiStateLiveData.observe(this@ShortsActivity) { it ->
            when (it) {
                is ShortsUiState.Loading -> {}
                is ShortsUiState.Success -> {
                    val list = it.youtubeData.items
                    list?.let { items -> currentList.addAll(items) }
                        .run { adapter.submitList(currentList.toMutableList()) }
                }

                is ShortsUiState.Error<*> -> {}
            }
        }
    }

    private fun RecyclerView.addScrollVisiblePositionListener() {
        this.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollVisiblePositionEvent(recyclerView)

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    pagingEvent(recyclerView)
                }
            }
        })

    }

    private fun scrollVisiblePositionEvent(recyclerView: RecyclerView) {
        val verticalScrollExtent = recyclerView.computeVerticalScrollExtent()
        if (verticalScrollExtent == 0) {
            return
        }
        val playablePosition =
            (recyclerView.computeVerticalScrollOffset()
                .toDouble() / verticalScrollExtent).roundToInt()
        if (scrollEvent.value != playablePosition) {
            scrollEvent.value = playablePosition
        }
    }

    private fun pagingEvent(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        // 스크롤이 마지막 아이템에 도달했는지 확인
        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
            // 추가 데이터를 가져오기 위한 API 요청 보내기
            viewModel.moreVideData()
            Timber.d("sjh last item start loading")
        }
    }

}