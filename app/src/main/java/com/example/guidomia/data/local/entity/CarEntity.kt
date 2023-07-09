package com.example.guidomia.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_cars")
data class CarEntity(
    val consList: List<String>,
    val customerPrice: Int,
    val make: String,
    val marketPrice: Int,
    val model: String,
    val prosList: List<String>,
    val rating: Int,
    val imageId: Int? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
