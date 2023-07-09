package com.example.guidomia.domain.repository

import com.example.guidomia.domain.model.Car

interface CarRepository {

    /**
     * @return List of car that we can either get from assets, local database, or from third-party API
     */
    suspend fun getAll(): List<Car>
}