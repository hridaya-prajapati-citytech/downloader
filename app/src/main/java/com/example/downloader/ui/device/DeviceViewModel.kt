package com.example.downloader.ui.device

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.downloader.data.local.LocalDeviceInfo
import com.example.downloader.database.AppDatabase
import com.example.downloader.network.RetrofitService
import com.example.downloader.repository.device.DefaultDeviceRepository
import com.example.downloader.repository.device.DeviceRepository
import com.example.downloader.ui.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DeviceViewModel(application: Application) : BaseViewModel(application) {
    private val _deviceInfo by lazy { MutableLiveData<LocalDeviceInfo>() }
    val deviceInfo: LiveData<LocalDeviceInfo> = _deviceInfo


    private val _loading by lazy { MutableLiveData(true) }
    val loading: LiveData<Boolean> = _loading

    private val deviceInfoRepository: DeviceRepository = DefaultDeviceRepository(
        RetrofitService().githubRetrofitService, AppDatabase.getDatabase(getApplication())
    )

    val disposables = CompositeDisposable()

    fun loadDeviceInfo(codename: String) {
        disposables.add(
            deviceInfoRepository.getDeviceInfo(codename)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { deviceInfo ->
                        _loading.value = false
                        _deviceInfo.value = deviceInfo
                    },

                    { error ->
                        //log
                    }, {
                        _loading.value = false
                    }

                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}