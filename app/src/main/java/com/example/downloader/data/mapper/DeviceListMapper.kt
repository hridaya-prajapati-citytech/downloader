package com.example.downloader.data.mapper

import com.example.downloader.data.local.LocalDevice
import com.example.downloader.data.network.NetworkDevice
import java.util.UUID

object DeviceListMapper {
    fun toLocalDeviceList(networkDeviceList: List<NetworkDevice>): List<LocalDevice> {
        return networkDeviceList.map { networkDevice ->
            LocalDevice(
                id = UUID.randomUUID(),
                isActive = networkDevice.isActive,
                codename = networkDevice.codename,
                codenameAlt = networkDevice.codenameAlt,
                lastUpdated = networkDevice.lastUpdated,
                maintainerName = networkDevice.maintainerName,
                model = networkDevice.model,
                vendor = networkDevice.vendor,
                version = networkDevice.version
            )
        }
    }
}