package com.example.downloader.data.mapper

import com.example.downloader.data.local.LocalDevice
import com.example.downloader.data.network.NetworkDevice
import com.example.downloader.data.ui.UIDevice
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

    fun toUIDeviceList(localDeviceList: List<LocalDevice>): List<UIDevice> {
        return localDeviceList.map { localDevice ->
            UIDevice(
                isActive = localDevice.isActive,
                codename = localDevice.codename,
                codenameAlt = localDevice.codenameAlt,
                lastUpdated = localDevice.lastUpdated,
                maintainerName = localDevice.maintainerName,
                model = localDevice.model,
                vendor = localDevice.vendor,
                version = localDevice.version,
                isRevealed = false
            )
        }
    }
}