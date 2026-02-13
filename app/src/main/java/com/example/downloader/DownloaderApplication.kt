package com.example.downloader

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.ketch.DownloadConfig
import com.ketch.Ketch
import com.ketch.NotificationConfig

class DownloaderApplication : Application() {
    lateinit var ketch: Ketch

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)

        ketch = Ketch.builder().setDownloadConfig(DownloadConfig()).setNotificationConfig(
            NotificationConfig(
                true, smallIcon = R.drawable.ic_launcher_foreground
            )
        ).enableLogs(true).build(this)

    }
}