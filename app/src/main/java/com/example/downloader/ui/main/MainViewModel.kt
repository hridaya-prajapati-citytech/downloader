package com.example.downloader.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.downloader.data.local.LocalDevice
import com.example.downloader.ui.common.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val _devices by lazy { MutableLiveData<List<LocalDevice>>() }
    val devices: LiveData<List<LocalDevice>> get() = _devices

}