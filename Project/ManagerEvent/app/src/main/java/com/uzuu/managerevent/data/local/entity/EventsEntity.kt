package com.uzuu.managerevent.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.math.hypot

@Entity(tableName = "events",
    foreignKeys = [
        ForeignKey(
            UsersEntity::class,
            parentColumns = ["id"],
            childColumns = ["organizerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            CategoriesEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index(value = ["organizerId"]),
        Index(value = ["categoryId"])
    ]
)
data class EventsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryId: Int,
    val organizerId: Int,
    val eventName: String,
    val location : String,
    val startTime : Long,
    val endTime: Long,
    val description: String,
    val status: Int
)
