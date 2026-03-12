package com.uzuu.managerevent.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.managerevent.data.local.entity.EventsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDao {
    @Query("select * from events")
    fun observeEvents() : Flow<List<EventsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createEvent(event: EventsEntity) : Long

    @Update
    suspend fun updateEvent(user: EventsEntity) : Int

    @Query("delete from events where id = :id")
    suspend fun deleteEventById(id: Int): Int

    @Query("select * from events where id = :id")
    suspend fun getEventById(id: Int) : EventsEntity?

    @Query("select exists (select 1 from events where id = :id)")
    suspend fun checkEventExists(id: Int) : Boolean
}