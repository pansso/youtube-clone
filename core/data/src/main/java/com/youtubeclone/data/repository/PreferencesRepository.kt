package com.youtubeclone.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.youtubeclone.model.YoutubePopularVideosData
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    val sharedPreferences: SharedPreferences,
    val json: Json
) {
    inline fun <reified T> saveData(key: String, data: T) {
        val jsonData = json.encodeToString(serializer<T>(), data)
        sharedPreferences.edit().putString(key, jsonData).apply()
    }

    inline fun <reified T> loadData(key: String): T? {
        val jsonData = sharedPreferences.getString(key, null)
        return jsonData?.let {
            json.decodeFromString(serializer<T>(), it)
        }
    }
}