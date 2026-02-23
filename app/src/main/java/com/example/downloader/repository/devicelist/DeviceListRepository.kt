package com.example.downloader.repository.devicelist

import com.example.downloader.data.local.LocalDevice
import com.example.downloader.data.network.NetworkDevice
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface DeviceListRepository {
    fun getDeviceList(): Maybe<List<LocalDevice>>
    fun getDeviceListFromLocal(): Single<List<LocalDevice>>
    fun getDeviceListFromRemote(): Maybe<List<NetworkDevice>>
    fun saveDeviceList(devices: List<LocalDevice>): Completable
    fun deleteDevices(): Completable
    fun deleteDeviceByCodename(codename: String): Completable
}