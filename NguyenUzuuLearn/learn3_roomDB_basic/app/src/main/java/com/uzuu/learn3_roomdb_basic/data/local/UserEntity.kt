package com.uzuu.learn3_roomdb_basic.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey val id: Int,
    val name: String
)