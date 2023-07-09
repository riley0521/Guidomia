package com.example.guidomia.data.repository

import android.content.Context
import com.example.guidomia.R
import com.example.guidomia.data.local.CarDao
import com.example.guidomia.data.local.entity.CarEntity
import com.example.guidomia.data.mapper.toCar
import com.example.guidomia.data.mapper.toCarEntity
import com.example.guidomia.domain.model.Car
import com.example.guidomia.domain.repository.CarRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream

/**
 * This class implements the CarRepository interface
 * @param context The android Context class
 * @param dispatcher It can either be Main, IO, or Default. But we should use IO because it is mainly used for input and output, and network requests.
 */
class CarRepositoryImpl(
    private val context: Context,
    private val carDao: CarDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CarRepository {

    override suspend fun setup() {
        if (carDao.getRowCount() > 0) {
            return
        }

        val carList: List<CarEntity> = getCarListFromJsonFile().map { it.toCarEntity() }

        carDao.insertMultiple(carList)
    }

    private suspend fun getCarListFromJsonFile(): List<Car> {
        return withContext(dispatcher) {
            try {
                val carListInputStream: InputStream = context.assets.open("car_list.json")
                val buffer: ByteArray = ByteArray(carListInputStream.available())
                carListInputStream.read(buffer)
                carListInputStream.close()

                val carListRaw = String(buffer)

                val carList: List<Car> = Json.decodeFromString(carListRaw)

                val carListWithImage: List<Car> = carList.mapIndexed { index, car ->
                    val imageId = when (index) {
                        0 -> R.drawable.range_rover
                        1 -> R.drawable.alpine_roadster
                        2 -> R.drawable.bmw_330i
                        else -> R.drawable.mercedez_benz_glc
                    }

                    car.copy(imageId = imageId)
                }

                carListWithImage
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    override suspend fun getAll(): List<Car> {
        return carDao.getAll().map { it.toCar() }
    }
}