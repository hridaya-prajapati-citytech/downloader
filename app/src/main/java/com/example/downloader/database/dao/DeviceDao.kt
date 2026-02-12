package com.example.downloader.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.downloader.data.local.DeviceInfo

@Dao
interface DeviceDao {
    @Query("SELECT * FROM device WHERE codename = :codename")
    suspend fun getDeviceInfoByCodename(codename: String): DeviceInfo
}

