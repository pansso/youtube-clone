package com.youtubeclone.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.search.SearchFragment
import com.youtubeclone.home.HomeFragment
import com.youtubeclone.main.databinding.ActivityMainBinding
import com.youtubeclone.shorts.ShortsFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val bottomNavView by lazy { binding.bottomNavView }
    private val topBar by lazy { binding.topBar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(topBar as Toolbar)

        initBottomNavView()

        replaceFragment(HomeFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searchItem -> {
//                addFragment(SearchFragment())
                showDialogFragment(SearchFragment())
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun initBottomNavView() {
        bottomNavView.setBackgroundColor(
            resources.getColor(
                com.youtubeclone.designsystem.R.color.primary,
                theme
            )
        )

        // 아이템 색상 설정
        bottomNavView.itemIconTintList = resources.getColorStateList(
            com.youtubeclone.designsystem.R.color.bottom_nav_item_color,
            theme
        )
        bottomNavView.itemTextColor = resources.getColorStateList(
            com.youtubeclone.designsystem.R.color.bottom_nav_item_color,
            theme
        )

        // 리플 색상 설정
        bottomNavView.itemRippleColor = resources.getColorStateList(
            com.youtubeclone.designsystem.R.color.bottom_nav_ripple_color,
            theme
        )
        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeMenuItem -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.shortsMenuItem -> {
                    replaceFragment(ShortsFragment())
                    true
                }

                R.id.subscriptionsMenuItem -> true
                R.id.libraryMenuItem -> true
                else -> false
            }

        }
    }

    private fun showDialogFragment(fragment: DialogFragment) {
        fragment.show(supportFragmentManager, "")
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        topBarVisible(fragment)
    }

    private fun topBarVisible(fragment: Fragment) {
        when (fragment) {
            is ShortsFragment -> hideTopBar()
            else -> showTopBar()
        }
    }


    private fun showTopBar() {
        topBar.visibility = View.VISIBLE
        updateMargin(topBar.height)
    }

    private fun hideTopBar() {
        topBar.visibility = View.GONE
        updateMargin(topBar.height)
    }

    private fun updateMargin(height: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root as ConstraintLayout)

        if (topBar.visibility == View.VISIBLE) {
            constraintSet.setMargin(
                binding.fragmentContainer.id,
                ConstraintSet.TOP,
                if (height != 0) height else 138
            )
            constraintSet.connect(
                binding.fragmentContainer.id,
                ConstraintSet.TOP,
                topBar.id,
                ConstraintSet.BOTTOM
            )
        } else {
            constraintSet.connect(
                binding.fragmentContainer.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            constraintSet.setMargin(binding.fragmentContainer.id, ConstraintSet.TOP, 0)
        }

        constraintSet.applyTo(binding.root as ConstraintLayout)
    }

}