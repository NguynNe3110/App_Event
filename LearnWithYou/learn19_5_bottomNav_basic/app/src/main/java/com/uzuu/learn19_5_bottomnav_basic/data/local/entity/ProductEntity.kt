package com.uzuu.learn19_5_bottomnav_basic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: Long,
    val description: String
)
