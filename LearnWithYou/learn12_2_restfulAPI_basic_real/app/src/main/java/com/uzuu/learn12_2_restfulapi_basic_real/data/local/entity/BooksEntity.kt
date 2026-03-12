package com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("books",
    foreignKeys = [
        ForeignKey(
            CategoriesEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    // tien, toi uu hieu suat doc bang
    indices = [
        Index(value = ["categoryId"])
    ]
)
data class BooksEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val quantity: Int,
    val categoryId: Int
)
