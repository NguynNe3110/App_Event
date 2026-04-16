package com.uzuu.petrolimex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.petrolimex.data.local.entity.TimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {
    @Query("SELECT * FROM time")
    fun observeTime(): Flow<TimeEntity?>

    @Update
    suspend fun updateTime(timeEntity: TimeEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTime(time: TimeEntity): Long
}