package com.uzuu.learn12_2_restfulapi_basic_real.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.UsersEntity
import kotlinx.coroutines.flow.Flow

@Dao()
interface UsersDao {
    @Query("select * from users order by id asc")
    fun observeUsers() : Flow<List<UsersEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UsersEntity) : Long

    @Update
    suspend fun updateUser(user: UsersEntity) : Int

    @Query("delete from users where id = :id")
    suspend fun deleteUserById(id: Int): Int

    @Query("delete from users where username = :username")
    suspend fun deleteUserByUsername(username: String): Int
    @Query("select * from users where id = :id limit 1")
    suspend fun getUserById(id: Int) : UsersEntity?

    @Query("delete from users")
    suspend fun deleteAllUser(): Int

    @Query("select exists(select 1 from users where id = :id)")
    suspend fun checkUserExists(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertAllUser(users : List<UsersEntity>)
}