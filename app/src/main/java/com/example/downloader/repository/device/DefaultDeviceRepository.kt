package com.example.downloader.repository.device

import com.example.downloader.data.local.LocalDeviceInfo
import com.example.downloader.data.mapper.DeviceInfoMapper
import com.example.downloader.data.network.NetworkDeviceInfo
import com.example.downloader.database.AppDatabase
import com.example.downloader.network.GithubApiService
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class DefaultDeviceRepository(
    private val retrofitService: GithubApiService,
    private val dbInstance: AppDatabase,
) : DeviceRepository {
    private val deviceDao by lazy { dbInstance.deviceDao() }

    override fun deleteDeviceInfo(codename: String): Completable {
        return deviceDao.deleteDeviceInfoByCodename(codename)
            .subscribeOn(Schedulers.io())
    }

    override fun getDeviceInfo(codename: String): Maybe<LocalDeviceInfo> {
        return getDeviceInfoFromLocal(codename)
            .subscribeOn(Schedulers.io())
            .switchIfEmpty(
                getDeviceInfoFromRemote(codename)
                    .flatMap { remoteDeviceInfo ->
                        val mapped = DeviceInfoMapper.toLocalDeviceInfoMapper(remoteDeviceInfo)
                        saveDeviceInfo(mapped)
                            .andThen(Maybe.just(mapped))
                    }
            )
    }

    override fun getDeviceInfoFromLocal(codename: String): Maybe<LocalDeviceInfo> {
        return deviceDao.getDeviceInfoByCodename(codename)
            .subscribeOn(Schedulers.io())
    }

    override fun getDeviceInfoFromRemote(codename: String): Maybe<NetworkDeviceInfo> {
        return retrofitService.getDeviceInfo(codename, "sixteen")
    }

    override fun saveDeviceInfo(localDeviceInfo: LocalDeviceInfo): Completable {
        return deviceDao.saveDeviceInfo(localDeviceInfo)
            .subscribeOn(Schedulers.io())
    }
}
