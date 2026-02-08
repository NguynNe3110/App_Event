package com.uzuu.learn14_1_db_deeplearn.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    // Bài 14.1: column này sẽ được thêm bằng Migration 1 -> 2
    val email: String = ""
)