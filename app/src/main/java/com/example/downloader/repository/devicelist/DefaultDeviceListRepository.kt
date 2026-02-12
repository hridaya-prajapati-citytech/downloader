package com.example.downloader.repository.devicelist

import com.example.downloader.data.local.LocalDevice
import com.example.downloader.data.mapper.DeviceListMapper
import com.example.downloader.data.network.NetworkDevice
import com.example.downloader.database.AppDatabase
import com.example.downloader.network.GithubApiService
import com.example.downloader.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DefaultDeviceListRepository(
    private val retrofitService: GithubApiService,
    private val dbInstance: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DeviceListRepository {
    private val deviceListDao by lazy { dbInstance.deviceListDao() }
    override suspend fun getDeviceList(): Result<List<LocalDevice>> = withContext(ioDispatcher) {
        getDeviceListFromLocal()?.let {
            Result.Success(it)
        } ?: when (val remoteDeviceInfo = getDeviceListFromRemote()) {
            is Result.Success -> {
                val localDeviceList = DeviceListMapper.toLocalDeviceList(remoteDeviceInfo.data)
                localDeviceList.forEach {
                    deviceListDao.saveDevice(it)
                }
                Result.Success(localDeviceList)
            }

            is Result.Error -> Result.Error(remoteDeviceInfo.exception)
            else -> {
                Result.Error(Exception("Unknown error"))
            }
        }
    }

    override suspend fun getDeviceListFromLocal(): List<LocalDevice>? = withContext(ioDispatcher) {
        deviceListDao.getDeviceList()
    }

    override suspend fun getDeviceListFromRemote(): Result<List<NetworkDevice>> {
        try {
            val deviceList = retrofitService.getDeviceList("sixteen")
            return Result.Success(deviceList.devices)
        } catch (e: HttpException) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun saveDeviceList(devices: List<LocalDevice>) = withContext(ioDispatcher) {
        devices.forEach {
            deviceListDao.saveDevice(it)
        }
    }

    override suspend fun deleteDevices() = withContext(ioDispatcher) {
        deviceListDao.deleteDeviceList()
    }

    override suspend fun deleteDeviceByCodename(codename: String) = withContext(ioDispatcher) {
        deviceListDao.deleteDeviceByCodename(codename)
    }
}