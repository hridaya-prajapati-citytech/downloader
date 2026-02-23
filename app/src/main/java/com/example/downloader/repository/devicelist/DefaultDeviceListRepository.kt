package com.example.downloader.repository.devicelist

import com.example.downloader.data.local.LocalDevice
import com.example.downloader.data.mapper.DeviceListMapper
import com.example.downloader.data.network.NetworkDevice
import com.example.downloader.database.AppDatabase
import com.example.downloader.network.GithubApiService
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class DefaultDeviceListRepository(
    private val retrofitService: GithubApiService,
    private val dbInstance: AppDatabase,
) : DeviceListRepository {
    private val deviceListDao by lazy { dbInstance.deviceListDao() }

    override fun getDeviceList(): Maybe<List<LocalDevice>> {
        return getDeviceListFromLocal()
            .subscribeOn(Schedulers.io())
            .flatMapMaybe { localList ->
                if (localList.isNotEmpty()) {
                    Maybe.just(localList)
                } else {
                    Maybe.empty()
                }
            }
            .switchIfEmpty(
                getDeviceListFromRemote()
                    .flatMap { remoteList ->
                        val mapped =
                            DeviceListMapper.toLocalDeviceList(remoteList)

                        saveDeviceList(mapped)
                            .andThen(Maybe.just(mapped))
                    }
            )
    }

    override fun getDeviceListFromLocal(): Single<List<LocalDevice>> {
        return deviceListDao.getDeviceList()
    }

    override fun getDeviceListFromRemote(): Maybe<List<NetworkDevice>> {
        return retrofitService
            .getDeviceList("sixteen")
            .map { it.devices }
            .filter { it.isNotEmpty() }
    }

    override fun saveDeviceList(devices: List<LocalDevice>): Completable {
        return Completable.concat(devices.map { device ->
            deviceListDao.saveDevice(device)
        }).subscribeOn(Schedulers.io())
    }

    override fun deleteDevices(): Completable {
        return deviceListDao.deleteDeviceList().subscribeOn(Schedulers.io())
    }

    override fun deleteDeviceByCodename(codename: String): Completable {
        return deviceListDao.deleteDeviceByCodename(codename).subscribeOn(Schedulers.io())
    }
}