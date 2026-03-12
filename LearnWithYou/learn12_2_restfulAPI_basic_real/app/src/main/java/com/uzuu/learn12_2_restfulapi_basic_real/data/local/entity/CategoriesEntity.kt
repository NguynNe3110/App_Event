package com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("categories")
data class CategoriesEntity(
    @PrimaryKey(true)
    val id: Int,
    val name: String
)
