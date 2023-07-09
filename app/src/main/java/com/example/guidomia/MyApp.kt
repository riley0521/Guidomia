package com.example.guidomia

import android.app.Application
import androidx.room.Room
import com.example.guidomia.data.local.CarDao
import com.example.guidomia.data.local.MyDatabase
import com.example.guidomia.data.repository.CarRepositoryImpl
import com.example.guidomia.domain.repository.CarRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        val database: MyDatabase = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java,
            "my_db"
        ).build()

        val carDao: CarDao = database.carDao

        carRepository = CarRepositoryImpl(applicationContext, carDao)
        GlobalScope.launch {
            carRepository.setup()
        }
    }
}