package com.uzuu.petrolimex.feature.main

import com.uzuu.petrolimex.domain.model.Fuel
import com.uzuu.petrolimex.domain.model.Time

data class MainUiState(
    val isLoading: Boolean = false,
    val time: Time = Time(""),
    val fuels: List<Fuel> = emptyList()
)
