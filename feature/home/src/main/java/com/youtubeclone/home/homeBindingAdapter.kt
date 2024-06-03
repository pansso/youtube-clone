package com.youtubeclone.home

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@BindingAdapter("setText")
fun TextView.setText(str: String?) {
    text = str ?: ""
}

@BindingAdapter("circleImage")
fun ImageView.imageCircleUrl(url: String?) {
    url?.let {
        load(it) {
            crossfade(false)
            placeholder(android.R.drawable.ic_menu_gallery)
            error(com.youtubeclone.designsystem.R.drawable.baseline_error_24)
            transformations(CircleCropTransformation())
        }
    }
}
@BindingAdapter("imageUrl")
fun ImageView.imageUrl(url: String?) {
    url?.let {
        load(it) {
            crossfade(true)
            placeholder(android.R.drawable.ic_menu_gallery)
            error(com.youtubeclone.designsystem.R.drawable.baseline_error_24)
        }
    }
}


@BindingAdapter("viewsCount")
fun TextView.viewCount(views: String?) {
    text = views?.toIntOrNull()?.let { it ->
        when {
            it >= 1_000_000_000 -> "${it / 1_000_000_000}B views"
            it >= 1_000_000 -> "${it / 1_000_000}M views"
            it >= 1_000 -> "${it / 1_000}K views"
            else -> "$it views"
        }
    } ?: "0 views"
}


@BindingAdapter("publishedDay")
fun TextView.setPublishedDay(publishedDay: String) {
    text = convert(publishedDay)
}

private fun convert(publishedDay: String): String {
    val formatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val publishedAt = LocalDateTime.parse(publishedDay, formatPattern)

    val currentDate = LocalDateTime.now().withNano(0)

    val differenceInSeconds = ChronoUnit.SECONDS.between(publishedAt, currentDate)
    val differenceInDays = ChronoUnit.DAYS.between(publishedAt, currentDate)
    val differenceInMonths = ChronoUnit.MONTHS.between(publishedAt, currentDate)
    return findDifference(differenceInSeconds, differenceInDays, differenceInMonths)
}

private fun findDifference(seconds: Long, days: Long, months: Long): String {
    return when {
        seconds < 60 -> "$seconds seconds ago"
        seconds < 3600 -> "${seconds / 60} minutes ago"
        seconds < 86400 -> "${seconds / 3600} hours ago"
        days < 30 -> "$days days ago"
        months < 12 -> "$months months ago"
        else -> "${months / 12} years ago"
    }
}