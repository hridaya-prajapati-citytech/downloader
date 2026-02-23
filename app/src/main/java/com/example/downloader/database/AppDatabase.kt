package com.example.downloader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.downloader.data.local.LocalDevice
import com.example.downloader.data.local.LocalDeviceInfo
import com.example.downloader.database.dao.DeviceDao
import com.example.downloader.database.dao.DeviceListDao

@Database(
    entities = [LocalDevice::class, LocalDeviceInfo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao
    abstract fun deviceListDao(): DeviceListDao

    companion object {
        const val DATABASE_NAME = "app_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }

        }
    }
}