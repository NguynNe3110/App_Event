package com.uzuu.managerevent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoriesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val cateName: String
)
