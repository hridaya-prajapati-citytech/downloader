package com.example.downloader.network

import com.example.downloader.data.network.DeviceList
import retrofit2.http.GET

interface GithubApiService {

    @GET("/official_devices/{version}/API/devices.json")
    suspend fun getDevicesList(version: String): DeviceList

}