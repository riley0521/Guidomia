package com.example.guidomia

import android.app.Application
import com.example.guidomia.data.repository.CarRepositoryImpl
import com.example.guidomia.domain.repository.CarRepository

/**
 * I created this class to simulate dependency injection without the use of Dagger Hilt for simplicity.
 */
class MyApp : Application() {

    /**
     * This field lateinit so we need to initialize this on onCreate() function, and we are sure that we can only mutate it in this class.
     */
    lateinit var carRepository: CarRepository
        private set

    override fun onCreate() {
        super.onCreate()

        carRepository = CarRepositoryImpl(applicationContext)
    }
}