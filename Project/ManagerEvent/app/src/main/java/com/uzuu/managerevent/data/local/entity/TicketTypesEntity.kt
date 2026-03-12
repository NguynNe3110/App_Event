package com.uzuu.managerevent.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "ticketTypes",
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
data class TicketTypesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val eventId: Int,
    val typeName: String,
    val price:  Long,
    val totalQuantity: Int,
    val remainingQuantity: Int,
    val description: String
)
