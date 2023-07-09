package com.example.guidomia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.guidomia.data.local.entity.CarEntity

@Database(
    entities = [CarEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {

    abstract val carDao: CarDao
}