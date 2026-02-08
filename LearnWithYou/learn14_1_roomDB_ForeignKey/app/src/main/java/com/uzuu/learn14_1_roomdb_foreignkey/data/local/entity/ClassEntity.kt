package com.uzuu.learn14_1_roomdb_foreignkey.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classes")
data class ClassEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = ""
)
