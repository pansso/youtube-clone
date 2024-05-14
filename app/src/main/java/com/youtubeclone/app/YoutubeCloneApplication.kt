package com.youtubeclone.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class YoutubeCloneApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}