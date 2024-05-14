package com.youtubeclone.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.compose.rememberNavController
import com.youtubeclone.home.HomeFragment
import com.youtubeclone.main.databinding.ActivityMainBinding
import com.youtubeclone.shorts.ShortsActivity
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

        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        if (fragment is ShortsFragment){
            hideTopBar()
        } else {
            showTopBar()
        }
    }

    private fun initView() {

        setContent {
            val navigator = rememberNavController()
            MainScreen(navigator)
        }
    }

}