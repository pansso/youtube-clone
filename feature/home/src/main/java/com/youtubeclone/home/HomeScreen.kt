package com.youtubeclone.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.youtubeclone.designsystem.Coral
import com.youtubeclone.designsystem.LightLime
import com.youtubeclone.designsystem.Teal

@Composable
fun HomeScreen(padding: PaddingValues) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(padding)
            .verticalScroll(scrollState)
    ) {
        for (i in 0..9) {
            when (i % 3) {
                0 -> ColorSample(color = Coral)
                1 -> ColorSample(color = LightLime)
                2 -> ColorSample(color = Teal)
            }
        }
    }
}

@Composable
fun ColorSample(color: Color) {
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(color)
    )
}