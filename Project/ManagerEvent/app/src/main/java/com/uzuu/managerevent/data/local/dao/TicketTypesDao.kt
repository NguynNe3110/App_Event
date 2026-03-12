package com.uzuu.managerevent.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.managerevent.data.local.entity.TicketTypesEntity

@Dao
interface TicketTypesDao {
    @Query("delete from ticketTypes where id = :id")
    suspend fun deleteTicketTypeById(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTicketType(ticketType: TicketTypesEntity): Long

    @Update
    suspend fun updateTicketType(ticketType: TicketTypesEntity): Int
}