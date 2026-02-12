package com.example.downloader.network

import com.example.downloader.data.network.NetworkDeviceInfo
import com.example.downloader.data.network.NetworkDeviceList
import retrofit2.http.GET

interface GithubApiService {

    @GET("/official_devices/{version}/API/devices.json")
    suspend fun getDeviceList(version: String): NetworkDeviceList

    @GET("/official_devices/{version}/API/devices/{codename}.json")
    suspend fun getDeviceInfo(codename: String, version: String): NetworkDeviceInfo
}