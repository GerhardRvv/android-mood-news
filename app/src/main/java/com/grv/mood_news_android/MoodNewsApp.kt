package com.grv.mood_news_android

import android.app.Application
import android.content.Context
import com.grv.common.util.SessionManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MoodNewsApp : Application() {

    @Inject
    lateinit var sessionManager: SessionManager
    lateinit var appContext: Context

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext

        sessionManager = SessionManager(this)
        sessionManager.setBaseUrl(getBaseUrl())
    }

    private fun getBaseUrl(): String {
        return appContext.applicationContext.resources.getString(R.string.base_url)
    }
}