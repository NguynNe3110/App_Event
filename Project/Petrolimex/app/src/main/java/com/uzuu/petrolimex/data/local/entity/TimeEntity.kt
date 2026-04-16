package com.uzuu.petrolimex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time")
data class TimeEntity(
    @PrimaryKey val id: Int = 0,
    val time: String
)
