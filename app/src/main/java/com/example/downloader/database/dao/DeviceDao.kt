package com.example.downloader.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.downloader.data.local.LocalDeviceInfo
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface DeviceDao {
    @Query("SELECT * FROM device_info WHERE codename = :codename")
    fun getDeviceInfoByCodename(codename: String): Maybe<LocalDeviceInfo>

    @Query("DELETE FROM device_info WHERE codename = :codename")
    fun deleteDeviceInfoByCodename(codename: String): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDeviceInfo(localDeviceInfo: LocalDeviceInfo): Completable
}