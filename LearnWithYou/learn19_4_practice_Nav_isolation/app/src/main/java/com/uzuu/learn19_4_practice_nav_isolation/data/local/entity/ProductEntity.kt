package com.uzuu.learn19_4_practice_nav_isolation.data.local.entity

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
