package com.example.downloader.data.mapper

import com.example.downloader.data.local.LocalDeviceInfo
import com.example.downloader.data.network.NetworkDeviceInfo
import java.util.UUID

object DeviceInfoMapper {
    fun toLocalDeviceInfoMapper(networkDeviceInfo: NetworkDeviceInfo): LocalDeviceInfo {
        return LocalDeviceInfo(
            id = UUID.randomUUID(),
            isActive = networkDeviceInfo.isActive,
            archive = networkDeviceInfo.archive,
            codename = networkDeviceInfo.codename,
            codenameAlt = networkDeviceInfo.codenameAlt,
            downloadLink = networkDeviceInfo.downloadLink,
            lastUpdated = networkDeviceInfo.lastUpdated,
            maintainer = MaintainerMapper.toLocalMaintainerMapper(networkDeviceInfo.maintainer),
            model = networkDeviceInfo.model,
            release = networkDeviceInfo.release,
            vendor = networkDeviceInfo.vendor,
            version = networkDeviceInfo.version,
            xda = networkDeviceInfo.xda
        )
    }
}