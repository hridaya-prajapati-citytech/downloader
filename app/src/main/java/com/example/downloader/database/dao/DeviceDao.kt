package com.example.downloader.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.downloader.data.local.LocalDeviceInfo

@Dao
interface DeviceDao {
    @Query("SELECT * FROM device WHERE codename = :codename")
    suspend fun getDeviceInfoByCodename(codename: String): LocalDeviceInfo?

    @Query("DELETE FROM device WHERE codename = :codename")
    suspend fun deleteDeviceInfoByCodename(codename: String)

    @Query("INSERT INTO device VALUES (:localDeviceInfo)")
    suspend fun saveDeviceInfo(localDeviceInfo: LocalDeviceInfo)
}