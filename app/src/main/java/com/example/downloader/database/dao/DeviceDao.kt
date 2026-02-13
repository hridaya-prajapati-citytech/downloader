package com.example.downloader.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.downloader.data.local.LocalDeviceInfo

@Dao
interface DeviceDao {
    @Query("SELECT * FROM device_info WHERE codename = :codename")
    suspend fun getDeviceInfoByCodename(codename: String): LocalDeviceInfo?

    @Query("DELETE FROM device_info WHERE codename = :codename")
    suspend fun deleteDeviceInfoByCodename(codename: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDeviceInfo(localDeviceInfo: LocalDeviceInfo)
}