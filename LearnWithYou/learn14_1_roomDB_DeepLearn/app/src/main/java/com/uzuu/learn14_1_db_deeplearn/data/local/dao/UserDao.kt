package com.uzuu.learn14_1_db_deeplearn.data.local.dao

import androidx.room.*
import com.uzuu.learn14_1_db_deeplearn.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserEntity): Long

    // READ
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun observeAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): UserEntity?

    // UPDATE
    @Update
    suspend fun update(user: UserEntity): Int

    // DELETE
    @Delete
    suspend fun delete(user: UserEntity): Int

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("DELETE FROM users")
    suspend fun clearAll(): Int
}