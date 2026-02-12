package com.example.downloader.repository.device

import com.example.downloader.data.local.LocalDeviceInfo
import com.example.downloader.data.network.NetworkDeviceInfo
import com.example.downloader.network.Result

interface DeviceRepository {
    suspend fun getDeviceInfo(codename: String): Result<LocalDeviceInfo>
    suspend fun getDeviceInfoFromLocal(codename: String): LocalDeviceInfo?
    suspend fun getDeviceInfoFromRemote(codename: String): Result<NetworkDeviceInfo>
    suspend fun saveDeviceInfo(localDeviceInfo: LocalDeviceInfo)
    suspend fun deleteDeviceInfo(codename: String)
}