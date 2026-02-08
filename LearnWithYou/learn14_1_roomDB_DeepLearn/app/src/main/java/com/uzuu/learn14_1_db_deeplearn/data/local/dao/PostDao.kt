package com.uzuu.learn14_1_db_deeplearn.data.local.dao

import androidx.room.*
import com.uzuu.learn14_1_db_deeplearn.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(post: PostEntity): Long

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun observeAll(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE userId = :userId ORDER BY id DESC")
    fun observeByUserId(userId: Long): Flow<List<PostEntity>>

    @Update
    suspend fun update(post: PostEntity): Int

    @Delete
    suspend fun delete(post: PostEntity): Int

    @Query("DELETE FROM posts")
    suspend fun clearAll(): Int
}