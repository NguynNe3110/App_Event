package com.uzuu.learn15_roomdb_retrofitapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.uzuu.learn15_roomdb_retrofitapi.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun observeAll(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearAll()
}