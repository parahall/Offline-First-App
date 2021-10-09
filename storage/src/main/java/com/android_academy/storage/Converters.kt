package com.android_academy.storage

import androidx.room.TypeConverter
import java.util.Date

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?) = value?.let{ Date(value) }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?) = date?.let{ date.time}

    @TypeConverter
    fun fromString(value: String?): List<Int>? {
        return value?.split(',')?.mapNotNull { it.toIntOrNull() }
    }

    @TypeConverter
    fun fromArrayList(list: List<Int>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String?): Map<String, String>? {
        if(value.isNullOrBlank()) return null
        return value.split(",").associate {
            val (left, right) = it.split("=")
            left to right
        }
    }

    @TypeConverter
    @JvmStatic
    fun mapToString(value: Map<String, String>?): String? {
        return value?.map {
            "${it.key}=${it.value}"
        }?.joinToString(",")
    }
}