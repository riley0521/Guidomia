package com.example.guidomia.data.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromListToString(value: List<String>): String {
        val listWithoutBlankItems: List<String> = value.takeIf { it.isNotEmpty() } ?: emptyList()
        return listWithoutBlankItems.joinToString(",")
    }
}