package com.example.downloader.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.downloader.data.local.Device

@Dao
interface DeviceListDao {
    @Query("SELECT * FROM device")
    suspend fun getDeviceList(): List<Device>
}