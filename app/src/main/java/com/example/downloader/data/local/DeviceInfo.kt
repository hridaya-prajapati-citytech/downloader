package com.example.downloader.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity("device_info")
data class DeviceInfo(
    @PrimaryKey val id: UUID,
    @ColumnInfo("active") val isActive: Boolean,
    @ColumnInfo("archive") val archive: String,
    @ColumnInfo("codename") val codename: String,
    @ColumnInfo("codename_alt") val codenameAlt: String,
    @ColumnInfo("download_link") val downloadLink: String,
    @ColumnInfo("last_updated") val lastUpdated: Int,
    @ColumnInfo("maintainer") val maintainer: List<Maintainer>,
    @ColumnInfo("model") val model: String,
    @ColumnInfo("release") val release: String,
    @ColumnInfo("vendor") val vendor: String,
    @ColumnInfo("version") val version: Int,
    @ColumnInfo("xda") val xda: String
)
