package com.example.downloader.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("device")
data class LocalDevice(
    @PrimaryKey val id: UUID,
    @ColumnInfo("active") val isActive: Boolean,
    @ColumnInfo("codename") val codename: String,
    @ColumnInfo("codename_alt") val codenameAlt: String,
    @ColumnInfo("last_updated") val lastUpdated: Int,
    @ColumnInfo("maintainer_name") val maintainerName: String,
    @ColumnInfo("model") val model: String,
    @ColumnInfo("vendor") val vendor: String,
    @ColumnInfo("version") val version: Int
)
