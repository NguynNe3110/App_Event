package com.uzuu.learn19_5_bottomnav_basic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.learn19_5_bottomnav_basic.data.local.entity.TimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {
    @Query("select * from times")
    fun observeTime() : Flow<List<TimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTime(time: TimeEntity): Long

    @Query("delete from times where id = :id")
    suspend fun deleteTimeById(id: Int)

    @Update
    suspend fun updateTime(time: TimeEntity) : Int

    @Query("select * from times where id = :id")
    suspend fun getTimeById(id: Int) : TimeEntity?
}