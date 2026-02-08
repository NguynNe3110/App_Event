package com.uzuu.learn14_1_db_deeplearn.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "posts",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE // xóa User -> xóa hết Post của User đó
        )
    ],
    indices = [
        Index(value = ["userId"]) // tối ưu query theo userId
    ]
)
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val userId: Long, // khóa ngoại

    val title: String
)