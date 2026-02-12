package com.example.downloader

import android.app.Application
import com.google.android.material.color.DynamicColors

class DownloaderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}