package com.youtubeclone.main.mainNavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.youtubeclone.designsystem.Black
import com.youtubeclone.designsystem.White
import com.youtubeclone.designsystem.YoutubeBlack
import com.youtubeclone.designsystem.YoutubeRed

@Composable
internal fun MainBottomNavigation(
    navigator: MainNavigator
) {
    val icons = bottomTabItems
    Row(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        icons.forEach { it ->
            MainBottomTabItem(tab = it, selected = it.route == navigator.currentRoute) {
                navigator.bottomTabNavigator(it)
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomTabItem(
    tab: MainTabs,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(onClick = { onClick() }),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(tab.resource),
                contentDescription = tab.text,
                tint = if (selected) {
                    Black
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                }
            )
            Text(text = tab.text)
        }

    }
}


private val bottomTabItems = listOf(
    MainTabs.BottomHome,
    MainTabs.BottomShorts,
    MainTabs.BottomSubscriptions,
    MainTabs.BottomLibrary
)
