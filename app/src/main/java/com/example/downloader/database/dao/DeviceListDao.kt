package com.example.downloader.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.downloader.data.local.LocalDevice

@Dao
interface DeviceListDao {
    @Query("SELECT * FROM device")
    suspend fun getDeviceList(): List<LocalDevice>

    @Query("DELETE FROM device WHERE codename = :codename")
    suspend fun deleteDeviceByCodename(codename: String)

    @Query("DELETE FROM device")
    suspend fun deleteDeviceList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDevice(localDevice: LocalDevice)
}