package com.example.downloader.ui.device

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.downloader.data.local.LocalDeviceInfo
import com.example.downloader.database.AppDatabase
import com.example.downloader.network.Result
import com.example.downloader.network.RetrofitService
import com.example.downloader.repository.device.DefaultDeviceRepository
import com.example.downloader.repository.device.DeviceRepository
import com.example.downloader.ui.common.BaseViewModel
import kotlinx.coroutines.launch

class DeviceViewModel(application: Application) : BaseViewModel(application) {
    private val _deviceInfo by lazy { MutableLiveData<LocalDeviceInfo>() }
    val deviceInfo: LiveData<LocalDeviceInfo> = _deviceInfo


    private val _loading by lazy { MutableLiveData<Boolean>(true) }
    val loading: LiveData<Boolean> = _loading

    private val deviceInfoRepository: DeviceRepository = DefaultDeviceRepository(
        RetrofitService().githubRetrofitService, AppDatabase.getDatabase(getApplication())
    )

    fun loadDeviceInfo(codename: String) {
        viewModelScope.launch {
            when (val deviceInfoResult = deviceInfoRepository.getDeviceInfo(codename)) {
                is Result.Success -> {
                    _loading.value = false
                    _deviceInfo.value = deviceInfoResult.data
                }

                is Result.Error -> {
                    Toast.makeText(
                        getApplication(), "${deviceInfoResult.exception}", Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    // noop
                }
            }
        }
    }
}