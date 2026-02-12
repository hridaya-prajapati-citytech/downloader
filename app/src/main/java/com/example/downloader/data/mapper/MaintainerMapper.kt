package com.example.downloader.data.mapper

import com.example.downloader.data.local.LocalMaintainer
import com.example.downloader.data.network.NetworkMaintainer
import java.util.UUID

object MaintainerMapper {
    fun toLocalMaintainerMapper(networkMaintainer: NetworkMaintainer): LocalMaintainer {
        return LocalMaintainer(
            maintainerName = networkMaintainer.maintainerName,
            maintainerGithub = networkMaintainer.maintainerGithub,
            maintainerTelegram = networkMaintainer.maintainerTelegram
        )

    }

    fun toLocalMaintainerMapper(networkMaintainer: List<NetworkMaintainer>): List<LocalMaintainer> {
        return networkMaintainer.map { toLocalMaintainerMapper(it) }
    }
}