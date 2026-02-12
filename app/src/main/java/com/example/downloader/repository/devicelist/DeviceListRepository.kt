package com.example.downloader.repository.devicelist

import com.example.downloader.data.local.LocalDevice
import com.example.downloader.data.network.NetworkDevice
import com.example.downloader.network.Result

interface DeviceListRepository {
    suspend fun getDeviceList(): Result<List<LocalDevice>>
    suspend fun getDeviceListFromLocal(): List<LocalDevice>?
    suspend fun getDeviceListFromRemote(): Result<List<NetworkDevice>>
    suspend fun saveDeviceList(devices: List<LocalDevice>)
    suspend fun deleteDevices()
    suspend fun deleteDeviceByCodename(codename: String)
}