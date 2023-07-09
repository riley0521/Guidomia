package com.example.guidomia.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.guidomia.data.local.entity.CarEntity

@Dao
interface CarDao {

    @Insert
    suspend fun insertMultiple(car: List<CarEntity>)

    @Query("SELECT * FROM tbl_cars")
    suspend fun getAll(): List<CarEntity>

    @Query("SELECT COUNT(*) FROM tbl_cars")
    suspend fun getRowCount(): Int
}