package com.example.downloader.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.downloader.data.local.LocalDevice
import com.example.downloader.database.AppDatabase
import com.example.downloader.network.Result
import com.example.downloader.network.RetrofitService
import com.example.downloader.repository.devicelist.DefaultDeviceListRepository
import com.example.downloader.repository.devicelist.DeviceListRepository
import com.example.downloader.ui.common.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val _devices by lazy { MutableLiveData<List<LocalDevice>>() }
    val devices: LiveData<List<LocalDevice>> get() = _devices


    private val deviceListRepository: DeviceListRepository = DefaultDeviceListRepository(
        RetrofitService().githubRetrofitService, AppDatabase.getDatabase(getApplication())
    )

    fun loadDeviceList() {
        viewModelScope.launch {
            when (val deviceList = deviceListRepository.getDeviceList()) {
                is Result.Success -> {
                    _devices.value = deviceList.data
                }

                else -> {
                    //noop
                }

            }
        }
    }
}