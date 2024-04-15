package com.youtubeclone.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.youtubeclone.designsystem.R
import com.youtubeclone.designsystem.YoutubeRed

@Composable
fun MainTopBanner() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.width(14.dp))
                AsyncImage(
                    model = R.drawable.ic_action_name,
                    contentDescription = "",
                    modifier = Modifier.fillMaxHeight()
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(text = "YouTubeClone")
            }

            Row {
                Box(modifier = Modifier.clickable {
                    Toast.makeText(context, "this is notification", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_notifications_24),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Box(modifier = Modifier.clickable {
                    Toast.makeText(context, "this is search", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_search_24),
                        contentDescription = ""
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Box(modifier = Modifier.clickable {
                    Toast.makeText(context, "this is profile", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_account_circle_24),
                        contentDescription = ""
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
            }
        }

    }
}

@Preview
@Composable
fun Preview() {
    MainTopBanner()
}
