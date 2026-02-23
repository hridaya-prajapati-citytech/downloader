package com.example.downloader.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.downloader.data.local.LocalDevice
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface DeviceListDao {
    @Query("SELECT * FROM device")
    fun getDeviceList(): Single<List<LocalDevice>>

    @Query("DELETE FROM device WHERE codename = :codename")
    fun deleteDeviceByCodename(codename: String): Completable

    @Query("DELETE FROM device")
    fun deleteDeviceList(): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDevice(localDevice: LocalDevice): Completable
}