package com.example.downloader.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("maintainer")
data class LocalMaintainer(
    @PrimaryKey val id: UUID,
    @ColumnInfo("display_name") val maintainerName: String,
    @ColumnInfo("github") val maintainerGithub: String,
    @ColumnInfo("telegram") val maintainerTelegram: String
)
