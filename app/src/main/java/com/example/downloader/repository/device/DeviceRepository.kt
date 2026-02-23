package com.example.downloader.repository.device

import com.example.downloader.data.local.LocalDeviceInfo
import com.example.downloader.data.network.NetworkDeviceInfo
import io.reactivex.Completable
import io.reactivex.Maybe

interface DeviceRepository {
    fun getDeviceInfo(codename: String): Maybe<LocalDeviceInfo>
    fun getDeviceInfoFromLocal(codename: String): Maybe<LocalDeviceInfo>
    fun getDeviceInfoFromRemote(codename: String): Maybe<NetworkDeviceInfo>
    fun saveDeviceInfo(localDeviceInfo: LocalDeviceInfo): Completable
    fun deleteDeviceInfo(codename: String): Completable
}