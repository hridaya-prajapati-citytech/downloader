package com.example.downloader.network

import com.example.downloader.data.network.NetworkDeviceInfo
import com.example.downloader.data.network.NetworkDeviceList
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {

    @GET("official_devices/{version}/API/devices.json")
    fun getDeviceList(@Path("version") version: String): Single<NetworkDeviceList>

    @GET("official_devices/{version}/API/devices/{codename}.json")
    fun getDeviceInfo(
        @Path("codename") codename: String,
        @Path("version") version: String
    ): Maybe<NetworkDeviceInfo>
}