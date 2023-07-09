package com.example.guidomia.presentation

import com.example.guidomia.domain.model.Car

data class HomeState(
    val carList: List<Car> = emptyList()
)
