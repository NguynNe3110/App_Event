package com.uzuu.petrolimex.data.remote.dto

import com.uzuu.petrolimex.domain.model.Fuel
import com.uzuu.petrolimex.domain.model.Time

data class FuelResponse(
    val time: Time,
    val fuels: List<Fuel>
)
