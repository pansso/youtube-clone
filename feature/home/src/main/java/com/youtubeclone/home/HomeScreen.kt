package com.youtubeclone.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import org.jetbrains.annotations.Async

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = "https://i.ytimg.com/vi/LDoZ4vOG8tM/hqdefault.jpg",
            contentDescription = "thumbnail image"
        )
        Text(
            text = "this is HomeScreen"
        )
    }
}