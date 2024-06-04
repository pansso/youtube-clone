package com.youtubeclone.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.compose.rememberNavController
import com.example.search.SearchFragment
import com.youtubeclone.home.HomeFragment
import com.youtubeclone.main.databinding.ActivityMainBinding
import com.youtubeclone.shorts.ShortsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val bottomNavView by lazy { binding.bottomNavView }
    private val topBar by lazy { binding.topBar }

    private fun showTopBar() {
        topBar.visibility = View.VISIBLE
    }

    private fun hideTopBar() {
        topBar.visibility = View.GONE
    }

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
                addFragment(SearchFragment())
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

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        topBarVisible(fragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()

        topBarVisible(fragment)
    }

    private fun topBarVisible(fragment: Fragment){
        when (fragment) {
            is ShortsFragment, is SearchFragment -> hideTopBar()
            else -> showTopBar()
        }
    }

}