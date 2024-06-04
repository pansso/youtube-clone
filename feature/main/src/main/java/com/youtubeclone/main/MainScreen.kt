package com.youtubeclone.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.youtubeclone.main.mainNavigation.MainBottomNavigation
import com.youtubeclone.main.mainNavigation.MainNavigator

@Composable
internal fun MainScreen(
    navigator: NavHostController,
) {
    val selectedTab = navigator.currentBackStackEntryAsState().value?.destination?.route
    val mainNavigator = MainNavigator(navigator)

    Scaffold(
        topBar = {
            MainTopBanner()
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                mainNavigator.MainNavigation(paddingValues)
            }
        },
        bottomBar = {
            MainBottomNavigation(
                navigator = mainNavigator
            )
        }
    )
}