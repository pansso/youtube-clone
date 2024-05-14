package com.youtubeclone.app

import android.content.Context
import timber.log.Timber

class AppInitProvider : InitProvider() {

    private var internalContext: Context? = null
    val appContext: Context
        get() = internalContext!!

    override fun onCreate(): Boolean {
        internalContext = context
        initTimber()
        return true
    }

    private fun initTimber() {
        // Timber Initialize
        if (BuildConfig.DEBUG) {
            // Timber Initialize
            Timber.uprootAll()
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    val threadName = Thread.currentThread().name
                    return "(${element.fileName}:${element.lineNumber})"
                }
            })
        }
    }
}