package com.example.downloader.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.downloader.data.local.LocalDevice
import com.example.downloader.database.AppDatabase
import com.example.downloader.network.RetrofitService
import com.example.downloader.repository.devicelist.DefaultDeviceListRepository
import com.example.downloader.repository.devicelist.DeviceListRepository
import com.example.downloader.ui.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val _devices by lazy { MutableLiveData<List<LocalDevice>>() }
    val devices: LiveData<List<LocalDevice>> get() = _devices

    private val disposables by lazy { CompositeDisposable() }

    private val deviceListRepository: DeviceListRepository = DefaultDeviceListRepository(
        RetrofitService().githubRetrofitService, AppDatabase.getDatabase(getApplication())
    )

    fun loadDeviceList() {
        disposables.add(
            deviceListRepository.getDeviceList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { devices ->
                        _devices.value = devices
                    },
                    { error ->
                        // handle error
                        Log.d(TAG, "loadDeviceList: $error")
                    },
                    {
                        _devices.value = emptyList()
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}