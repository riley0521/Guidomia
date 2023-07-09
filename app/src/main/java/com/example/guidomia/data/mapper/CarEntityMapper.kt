package com.example.guidomia.data.mapper

import com.example.guidomia.data.local.entity.CarEntity
import com.example.guidomia.domain.model.Car

fun CarEntity.toCar(): Car {
    return Car(
        consList = consList,
        customerPrice = customerPrice,
        make = make,
        marketPrice = marketPrice,
        model = model,
        prosList = prosList,
        rating = rating,
        imageId = imageId
    )
}

fun Car.toCarEntity(): CarEntity {
    return CarEntity(
        consList = consList,
        customerPrice = customerPrice,
        make = make,
        marketPrice = marketPrice,
        model = model,
        prosList = prosList,
        rating = rating,
        imageId = imageId,
    )
}