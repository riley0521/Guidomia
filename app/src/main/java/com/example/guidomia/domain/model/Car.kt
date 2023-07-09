package com.example.guidomia.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Car(
    val consList: List<String>,
    val customerPrice: Int,
    val make: String,
    val marketPrice: Int,
    val model: String,
    val prosList: List<String>,
    val rating: Int,
    val imageId: Int? = null
)
