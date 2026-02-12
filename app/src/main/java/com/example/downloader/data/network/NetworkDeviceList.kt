package com.example.downloader.data.network

import com.google.gson.annotations.SerializedName

data class NetworkDeviceList(
    @SerializedName("devices") val devices: List<NetworkDevice>
)