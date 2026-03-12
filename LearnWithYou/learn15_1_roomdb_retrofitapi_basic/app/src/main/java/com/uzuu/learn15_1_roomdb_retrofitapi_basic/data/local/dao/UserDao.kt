package com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun observeAll(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearAll()
}