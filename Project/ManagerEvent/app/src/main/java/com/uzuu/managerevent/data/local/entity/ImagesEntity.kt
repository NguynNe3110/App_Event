package com.uzuu.managerevent.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "images",
    foreignKeys = [
        ForeignKey(
            EventsEntity::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["eventId"])
    ]
)
data class ImagesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val eventId: Int,
    val urlImage: String
)
