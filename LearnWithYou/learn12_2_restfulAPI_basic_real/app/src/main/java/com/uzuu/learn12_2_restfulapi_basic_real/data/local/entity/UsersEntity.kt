package com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
data class UsersEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val password: String,
    val fullName: String
)
