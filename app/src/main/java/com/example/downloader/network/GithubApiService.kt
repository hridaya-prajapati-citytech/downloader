package com.example.downloader.network

import com.example.downloader.data.network.NetworkDeviceInfo
import com.example.downloader.data.network.NetworkDeviceList
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {

    @GET("official_devices/{version}/API/devices.json")
    suspend fun getDeviceList(@Path("version") version: String): NetworkDeviceList

    @GET("official_devices/{version}/API/devices/{codename}.json")
    suspend fun getDeviceInfo(@Path("codename") codename: String, @Path("version") version: String): NetworkDeviceInfo
}