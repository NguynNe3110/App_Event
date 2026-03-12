package com.uzuu.learn14_1_roomdb_foreignkey.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users",
    foreignKeys = [
        ForeignKey(
            ClassEntity::class,
            parentColumns = ["id"],
            childColumns = ["idClass"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    // tien, toi uu hieu suat doc bang
    indices = [
        Index(value = ["idClass"])
    ]
)
data class UserEntity(
    @PrimaryKey()
    val id: Int,
    val idClass: Int, //khoa ngoai
    val nameStudent: String = ""
)
