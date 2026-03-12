package com.uzuu.learn19_5_bottomnav_basic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "times")
data class TimeEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timeStart : Long,
    val timeEnd: Long
)
