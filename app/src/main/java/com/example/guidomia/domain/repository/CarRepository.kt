package com.example.guidomia.domain.repository

import com.example.guidomia.domain.model.Car

interface CarRepository {

    /**
     * This method should only be called in the onCreate() on MyApp.kt that extends the Application class.
     * This purpose is to get the car list from the json file in assets folder and save it to local database using Room.
     */
    suspend fun setup()

    /**
     * @return List of car that we can either get from assets, local database, or from third-party API
     */
    suspend fun getAll(): List<Car>
}