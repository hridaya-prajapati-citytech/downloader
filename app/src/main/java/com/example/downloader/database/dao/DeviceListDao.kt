package com.example.downloader.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.downloader.data.local.LocalDevice

@Dao
interface DeviceListDao {
    @Query("SELECT * FROM device")
    suspend fun getDeviceList(): List<LocalDevice>?

    @Query("DELETE FROM device WHERE codename = :codename")
    suspend fun deleteDeviceByCodename(codename: String)

    @Query("DELETE FROM device")
    suspend fun deleteDeviceList()

    @Query("INSERT INTO device VALUES (:localDevice)")
    suspend fun saveDevice(localDevice: LocalDevice)
}