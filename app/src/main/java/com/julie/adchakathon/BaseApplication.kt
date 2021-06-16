package com.julie.adchakathon

import android.app.Application
import android.content.Context
import com.julie.adchakathon.utils.SharedPrefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {

    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        var prefs: SharedPrefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPrefs(applicationContext)
    }
}