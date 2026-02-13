package com.example.downloader.repository.device

import com.example.downloader.data.local.LocalDeviceInfo
import com.example.downloader.data.mapper.DeviceInfoMapper
import com.example.downloader.data.network.NetworkDeviceInfo
import com.example.downloader.database.AppDatabase
import com.example.downloader.network.GithubApiService
import com.example.downloader.network.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DefaultDeviceRepository(
    private val retrofitService: GithubApiService,
    private val dbInstance: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DeviceRepository {
    private val deviceDao by lazy { dbInstance.deviceDao() }

    override suspend fun deleteDeviceInfo(codename: String) = withContext(ioDispatcher) {
        deviceDao.deleteDeviceInfoByCodename(codename)
    }

    override suspend fun saveDeviceInfo(localDeviceInfo: LocalDeviceInfo) =
        withContext(ioDispatcher) {
            deviceDao.saveDeviceInfo(localDeviceInfo)
        }

    override suspend fun getDeviceInfoFromLocal(codename: String): LocalDeviceInfo? =
        withContext(ioDispatcher) {
            deviceDao.getDeviceInfoByCodename(codename)
        }

    override suspend fun getDeviceInfoFromRemote(codename: String): Result<NetworkDeviceInfo> {
        try {
            val deviceInfo = retrofitService.getDeviceInfo(codename, "sixteen")
            return Result.Success(deviceInfo)
        } catch (e: HttpException) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getDeviceInfo(codename: String): Result<LocalDeviceInfo> =
        withContext(ioDispatcher) {
            val localDeviceInfo = getDeviceInfoFromLocal(codename)

            if (localDeviceInfo != null) {
                Result.Success(localDeviceInfo)
            } else {
                when (val remoteDeviceInfo = getDeviceInfoFromRemote(codename)) {
                    is Result.Success -> {
                        val localDevice =
                            DeviceInfoMapper.toLocalDeviceInfoMapper(remoteDeviceInfo.data)
                        deviceDao.saveDeviceInfo(localDevice)
                        Result.Success(localDevice)
                    }

                    is Result.Error -> Result.Error(remoteDeviceInfo.exception)
                    else -> {
                        Result.Error(Exception("Unknown error"))
                    }
                }
            }
        }
}
