package com.example.downloader.data.network

import com.google.gson.annotations.SerializedName

data class DeviceList(
    @SerializedName("devices") val devices: List<Device>
)