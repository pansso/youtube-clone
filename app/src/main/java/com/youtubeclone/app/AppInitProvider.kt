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
        Timber.plant(Timber.DebugTree())
    }

}