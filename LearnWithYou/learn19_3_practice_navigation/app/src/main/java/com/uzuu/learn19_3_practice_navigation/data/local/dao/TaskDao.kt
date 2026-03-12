package com.uzuu.learn19_3_practice_navigation.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.learn19_3_practice_navigation.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("select * from tasks order by id asc")
    fun observeAll(): Flow<List<TaskEntity>>

    @Query("select * from tasks where id = :id limit 1")
    fun observeById(id: Int): Flow<TaskEntity?> // co the null

    @Update
    suspend fun update(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<TaskEntity>)

    @Query("select count(*) from tasks")
    suspend fun count() : Int
}