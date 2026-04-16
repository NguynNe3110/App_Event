package com.uzuu.petrolimex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uzuu.petrolimex.domain.model.Fuel

@Entity(tableName = "fuel")
data class FuelEntity(
    @PrimaryKey val name: String,
    val price1: String,
    val price2: String
)
