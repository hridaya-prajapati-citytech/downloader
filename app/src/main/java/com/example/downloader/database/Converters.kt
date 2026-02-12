package com.example.downloader.database

import androidx.room.TypeConverter
import com.example.downloader.data.local.LocalMaintainer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromMaintainerList(value: List<LocalMaintainer>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMaintainerList(value: String): List<LocalMaintainer> {
        val listType = object : TypeToken<List<LocalMaintainer>>() {}.type
        return gson.fromJson(value, listType)
    }
}