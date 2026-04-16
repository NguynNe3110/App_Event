package com.uzuu.petrolimex.data.mapper

import com.uzuu.petrolimex.data.local.entity.FuelEntity
import com.uzuu.petrolimex.data.local.entity.TimeEntity
import com.uzuu.petrolimex.domain.model.Fuel
import com.uzuu.petrolimex.domain.model.Time

fun Fuel.fuelDomainToEntity() : FuelEntity{
    return FuelEntity(
        name = name,
        price1 = price1,
        price2 = price2
    )
}

fun FuelEntity.fuelEntityToDomain() : Fuel{
    return Fuel(
        name = name,
        price1 = price1,
        price2 = price2
    )
}

fun TimeEntity.timeEntityToDomain() : Time{
    return Time(
        time = time
    )
}

fun Time.timeDomainToEntity() : TimeEntity{
    return TimeEntity(
        time = time
    )
}